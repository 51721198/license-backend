package com.vico.license.dao;

import com.vico.license.pojo.RSAKey;
import org.springframework.stereotype.Component;

@Component
public interface RSAKeyDao {

    int insertRSAKeyPair(RSAKey rsakeypair);

    RSAKey selectKeyByID();

}
