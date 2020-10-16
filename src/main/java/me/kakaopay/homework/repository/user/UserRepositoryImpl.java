/*
 * Copyright (c) 2018 LINE Corporation. All rights reserved.
 * LINE Corporation PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package me.kakaopay.homework.repository.user;

import me.kakaopay.homework.entity.User;
import me.kakaopay.homework.repository.AbstractRepositorySupport;

public class UserRepositoryImpl extends AbstractRepositorySupport implements UserRepositoryCustom {
    public UserRepositoryImpl() {
        super(User.class);
    }
}
