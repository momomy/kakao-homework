package me.kakaopay.homework.repository.user;

import me.kakaopay.homework.entity.User;
import me.kakaopay.homework.repository.AbstractRepositorySupport;

public class UserRepositoryImpl extends AbstractRepositorySupport implements UserRepositoryCustom {
    public UserRepositoryImpl() {
        super(User.class);
    }
}
