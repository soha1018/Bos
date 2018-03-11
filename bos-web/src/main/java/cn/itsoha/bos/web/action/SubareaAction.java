package cn.itsoha.bos.web.action;

import cn.itcast.bos.utils.FileUtils;
import cn.itsoha.bos.domain.Region;
import cn.itsoha.bos.domain.Subarea;
import cn.itsoha.bos.service.SubareaService;
import cn.itsoha.bos.web.action.base.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import java.util.List;

@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea>{
    @Resource(name = "subareaServiceImpl")
    private SubareaService subareaService;

    /**
     * 添加分区
     */
    public String add() throws Exception {
        subareaService.save(model);
        return LIST;
    }

    /**
     * 分区的分页查询
     */

    public String pageQuery() throws Exception{
        DetachedCriteria criteria = pageBean.getCriteria();
        String addresskey = model.getAddresskey();
        //添加一个普通的查找条件
        if (StringUtils.isNotBlank(addresskey)) {
            criteria.add(Restrictions.like("addresskey","%"+addresskey+"%"));
        }
        Region region = model.getRegion();
        //添加一个多表查询的条件
        if (region != null) {
            String province = region.getProvince();
            String city = region.getCity();
            String district = region.getDistrict();
            //创建一个内连接关联多表查询
            criteria.createAlias("region","r");
            if (StringUtils.isNotBlank(province)) {
                criteria.add(Restrictions.like("r.province","%"+province+"%"));
            }
            if (StringUtils.isNotBlank(city)) {
                criteria.add(Restrictions.like("r.city","%"+city+"%"));
            }
            if (StringUtils.isNotBlank(district)) {
                criteria.add(Restrictions.like("r.district","%"+district+"%"));
            }
        }

        //查询分页数据
        subareaService.pageQuery(pageBean);
        //封装成json发送给页面
        this.java2json(pageBean, new String[]{"currentPage", "criteria", "pageSize","subareas"});
        return NONE;
    }


    /**
     * 分区导出数据
     */
    public String exportXls() throws Exception {
        //1.查询数据
        List<Subarea> list = subareaService.findAll();
        //2.写入POI中
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("分区数据");
        HSSFRow row = sheet.createRow(0);
        row.createCell(0).setCellValue("分区编号");
        row.createCell(1).setCellValue("开始编号");
        row.createCell(2).setCellValue("结束编号");
        row.createCell(3).setCellValue("位置信息");
        row.createCell(4).setCellValue("省市区");
        for (Subarea subarea:list) {
            HSSFRow hssfRow = sheet.createRow(sheet.getLastRowNum() + 1);
            hssfRow.createCell(0).setCellValue(subarea.getId());
            hssfRow.createCell(1).setCellValue(subarea.getStartnum());
            hssfRow.createCell(2).setCellValue(subarea.getEndnum());
            hssfRow.createCell(3).setCellValue(subarea.getPosition());
            hssfRow.createCell(4).setCellValue(subarea.getRegion().getName());
        }
        //3.使用输出流进行文件下载
        ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
        String fileName = "分区数据.xls";
        String mimeType = ServletActionContext.getServletContext().getMimeType(fileName);
        ServletActionContext.getResponse().setContentType(mimeType);
        String agent = ServletActionContext.getRequest().getHeader("User-Agent");
        String filename = FileUtils.encodeDownloadFilename(fileName, agent);
        ServletActionContext.getResponse().setHeader("content-disposition","attachment;filename="+filename);
        workbook.write(out);
        return NONE;
    }

    /**
     * 查询所有未关联到分区的定区
     */
    public String listAjax() throws Exception {
        List<Subarea> list = subareaService.findListByNotAssociation();
        this.java2json(list,new String[]{"decidedzone","region"});
        return NONE;
    }
}
