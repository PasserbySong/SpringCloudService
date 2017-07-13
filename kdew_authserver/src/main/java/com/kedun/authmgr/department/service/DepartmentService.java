package com.kedun.authmgr.department.service;

import com.kedun.authmgr.common.CommonTools;
import com.kedun.authmgr.common.ErrorCodeEnum;
import com.kedun.authmgr.exception.ServiceException;
import com.kedun.authmgr.department.dao.DepartmentInfoMapper;
import com.kedun.authmgr.department.model.DepartmentInfo;
import com.kedun.authmgr.department.vo.DepartmentVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * department Sevice层
 */
@Service
public class DepartmentService {

    @Autowired
    private DepartmentInfoMapper departmentInfoMapper;

    /**
     * 创建部门单位信息
     *
     * @param record
     * @return
     */
    public Object operateDepartment(DepartmentVo record) {

        DepartmentInfo di = new DepartmentInfo();

        BeanUtils.copyProperties(record, di);

        if (di.getParentId() == null || di.getParentId() == 0) {
            di.setParentId(0);
            di.setLinkPath(di.getDeptName());
        } else {
            //查询直属部门的linkPath
            DepartmentInfo parent = departmentInfoMapper.selectByPrimaryKey(di.getParentId());
            if (parent == null) {
                throw new ServiceException(ErrorCodeEnum.ParentDepartmentNoExist);
            }
            di.setLinkPath(parent.getLinkPath() + ">" + di.getDeptName());
        }

        if(di.getDeptId()==null){
            di.setCreateDt(new Date());
            departmentInfoMapper.insert(di);

        }else{
            di.setUpdateDt(new Date());
            departmentInfoMapper.updateByPrimaryKey(di);
        }
        return di.getDeptId();

    }

    /**
     * 查询部门详细信息
     * @param deptId
     * @return
     */
    public Object info(Integer deptId) {
        return departmentInfoMapper.selectByPrimaryKey(deptId);
    }

    /**
     * 获取部门结构树
     * @return
     */
    public Object treeInfo() {

        //查询所有的单位部门 有序
        List<Map<String, Object>> allList = departmentInfoMapper.selectAll();

        if (allList.size() != 0) {

            return this.getChildren(0,allList);
        }else{
            return new ArrayList<>();
        }
    }

    /**
     * 查询国标
     * @param type 查询目标 0 省 1 市 2 县 3 乡镇 4 村
     * @param id 省 id为0 其他 所属上级单位
     * @return
     */
    public Object queryGB(Integer type, String id) {
        return departmentInfoMapper.selectGB(type, id);
    }

    public Object delete(Integer deptID){
        return departmentInfoMapper.deleteByPrimaryKey(deptID);
    }

    /**
     * 验证部门是否存在 存在返回 部门名称
     * @param deptId
     * @return
     */
    public String verify(Integer deptId){
        DepartmentInfo departmentInfo=departmentInfoMapper.selectByPrimaryKey(deptId);
        if(departmentInfo==null){
            throw CommonTools.createException(ErrorCodeEnum.DepartmentNoExist);
        }

        return departmentInfo.getDeptName();
    }

    /**
     * 递归获取结构树
     * @param parentID
     * @param allList
     * @return
     */
    private List<Map<String, Object>> getChildren(Integer parentID, List<Map<String, Object>> allList) {
        boolean isQueryTarget = false;//是否检索到目标
        List<Map<String, Object>> childList = new ArrayList<>();
        for (Map<String, Object> map : allList) {
            if (parentID == map.get("parentId")) {
                map.put("children",this.getChildren(Integer.parseInt(map.get("deptId").toString()),allList));
                childList.add(map);
                isQueryTarget = true;
            } else if (isQueryTarget) {
                //找到目标并匹配结束
                break;
            }
        }
        return childList;
    }

}
