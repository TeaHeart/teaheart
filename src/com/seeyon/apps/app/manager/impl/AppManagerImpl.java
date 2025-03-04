package com.seeyon.apps.app.manager.impl;

import com.seeyon.apps.app.dao.AppDao;
import com.seeyon.apps.app.manager.AppManager;
import com.seeyon.apps.app.po.App;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.annotation.Inject;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AppManagerImpl implements AppManager {
    @Inject
    private AppDao appDao;

    @Override
    public void save(App app) throws BusinessException {
        app.setIdIfNew();
        appDao.insert(app);
    }

    @Override
    public void delete(List<String> ids) throws BusinessException {
        appDao.delete(ids.stream().map(String::trim).map(Long::parseLong).collect(Collectors.toList()));
    }

    @Override
    public FlipInfo list(FlipInfo flipInfo, Map<String, Object> params) throws BusinessException {
        appDao.select(flipInfo);
        return flipInfo;
    }
}
