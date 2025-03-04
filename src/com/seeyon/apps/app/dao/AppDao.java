package com.seeyon.apps.app.dao;

import com.seeyon.apps.app.po.App;
import com.seeyon.ctp.util.FlipInfo;

import java.util.List;

public interface AppDao {
    void insert(App app);

    void delete(List<Long> ids);

    void select(FlipInfo flipInfo);
}
