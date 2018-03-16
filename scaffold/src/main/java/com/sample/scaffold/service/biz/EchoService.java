package com.sample.scaffold.service.biz;

import org.springframework.stereotype.Service;

@Service
public class EchoService implements IEchoService {

    @Override
    public String echo() throws Exception {
        return "scaffold";
    }
}
