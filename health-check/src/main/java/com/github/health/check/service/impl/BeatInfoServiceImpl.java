package com.github.health.check.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.health.check.domain.entity.Project;
import com.github.health.check.enums.ErrorCode;
import com.github.health.check.exception.BusinessException;
import com.github.health.check.mapper.BeatInfoMapper;
import com.github.health.check.domain.entity.BeatInfo;
import com.github.health.check.mapper.ProjectMapper;
import com.github.health.check.service.BeatInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BeatInfoServiceImpl extends ServiceImpl<BeatInfoMapper, BeatInfo> implements BeatInfoService {

    @Resource
    private BeatInfoMapper beatInfoMapper;

    @Resource
    private ProjectMapper projectMapper;

    @Override
    public void createBeat(BeatInfo beatInfo) {
        checkBeat(beatInfo.getOwner(), beatInfo.getName(), beatInfo.getCode());
        heatLimit(beatInfo.getServiceName());
        beatInfo.setStatus("N");

        beatInfoMapper.insert(beatInfo);
    }

    @Override
    public BeatInfo getLastBeatInfo(String projectName, String checkName, Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        BeatInfo beatInfo = beatInfoMapper.getLastBeatInfo(projectName,checkName,dateString);
        return beatInfo;
    }

    @Override
    public BeatInfo getLastBeatInfoWithoutTime(String projectName, String checkName) {
        BeatInfo beatInfo = beatInfoMapper.getLastBeatInfoWithoutTime(projectName,checkName);
        return beatInfo;
    }


    /**
     *  根据项目名(projectName)和编码(code)判断是否是应用上报的心跳
     * @param owner
     * @param projectName
     * @param code
     * @return
     */
    private void checkBeat(String owner, String projectName, String code) {
        Project project = projectMapper.queryProject(owner, projectName, code);
        if (project == null) {
            throw new BusinessException(ErrorCode.PROJECT_NOT_FOUND.getCode(),ErrorCode.PROJECT_NOT_FOUND.getMsg());
        }
    }

    /**
     * 检测服务发送次数是否达到上限
     * @param serviceName
     */
    private void heatLimit(String serviceName) {

    }
}
