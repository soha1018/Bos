package cn.itsoha.bos.web.action;

import cn.itsoha.bos.domain.Region;
import cn.itsoha.bos.service.RegionService;
import cn.itsoha.bos.utils.PinYin4jUtils;
import cn.itsoha.bos.web.action.base.BaseAction;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
    private File regionFile;
    @Resource(name = "regionServiceImpl")
    private RegionService regionService;
    //分区查询
    private String q;


    /**
     * 导入文件
     */
    public String importXls() throws Exception {
        ArrayList<Region> list = new ArrayList<>();
        //读取xls文件的内容
        HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
        HSSFSheet sheet = workbook.getSheetAt(0);
        for (Row row : sheet) {
            //过滤第一行标题的内容
            int rowNum = row.getRowNum();
            if (rowNum == 0) {
                continue;
            }
            String id = row.getCell(0).getStringCellValue();
            String province = row.getCell(1).getStringCellValue();
            String city = row.getCell(2).getStringCellValue();
            String district = row.getCell(3).getStringCellValue();
            String postcode = row.getCell(4).getStringCellValue();

            Region region = new Region(id, province, city, district, postcode, null, null, null);
            //对字符串进行简写和转拼音操作
            province = province.substring(0, province.length() - 1);
            city = city.substring(0, city.length() - 1);
            district = district.substring(0, district.length() - 1);

            String info = province + city + district;
            String[] head = PinYin4jUtils.getHeadByString(info);
            //去掉里面的空格
            String join = StringUtils.join(head);

            //拼音简写
            String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
            region.setShortcode(join);
            region.setCitycode(cityCode);

            list.add(region);
        }
        //保存批量数据
        regionService.saveBatch(list);
        return NONE;
    }

    /**
     * 分页查询
     */
    public String pageQuery() throws Exception {
        //查询分页数据
        regionService.pageQuery(pageBean);
        //封装成json发送给页面
        this.java2json(pageBean, new String[]{"currentPage", "criteria", "pageSize","subareas"});
        return NONE;
    }


    /**
     * 分区查询
     */
    public String listajax() throws Exception {
        List<Region> list = null;
        if (StringUtils.isNotBlank(q)) {
            list = regionService.findListByQ(q);
        } else {
            list = regionService.findAll();
        }
        this.java2json(list,new String[]{"subareas"});
        return NONE;
    }


    public File getRegionFile() {
        return regionFile;
    }

    public void setRegionFile(File regionFile) {
        this.regionFile = regionFile;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}
