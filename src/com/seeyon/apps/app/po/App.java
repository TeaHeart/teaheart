package com.seeyon.apps.app.po;

import com.seeyon.ctp.common.po.BasePO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class App extends BasePO {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String key;
    private String value;
}
