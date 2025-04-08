package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.Account;
import org.example.eth.GethService;
import org.example.mapper.AccountMapper;
import org.example.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements AccountService {
    @Autowired
    GethService gethService;

    /**
     * 保存一个 account, 如果 token 为 null, 调用 {@link GethService#newAccount(String)} 进行注册用户
     *
     * @param account 实体对象
     * @return 是否保存成功
     */
    @Override
    public boolean save(Account account) {
        if (account.getToken() == null) {
            account.setToken(gethService.newAccount(account.getPassword()));
        }
        return super.save(account);
    }
}
