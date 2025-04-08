package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.Account;
import org.example.domain.ItemRepository;
import org.example.eth.ItemService;
import org.example.mapper.AccountMapper;
import org.example.mapper.ItemRepositoryMapper;
import org.example.service.ItemRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemRepositoryServiceImpl extends ServiceImpl<ItemRepositoryMapper, ItemRepository> implements ItemRepositoryService {
    @Autowired
    ItemService itemService;
    @Autowired
    AccountMapper accountMapper;

    /**
     * 保存一个 itemRepository 如果 token 为 null, 调用 {@link ItemService#mint(String)} 进行注册道具
     *
     * @param itemRepository 实体对象
     * @return 是否保存成功
     */
    @Override
    public boolean save(ItemRepository itemRepository) {
        if (itemRepository.getToken() == null) {
            Account account = accountMapper.selectById(itemRepository.getAccountId());
            itemRepository.setToken(itemService.mint(account.getToken()).item);
        }
        return super.save(itemRepository);
    }
}
