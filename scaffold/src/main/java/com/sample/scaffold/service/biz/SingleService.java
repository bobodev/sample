package com.sample.scaffold.service.biz;

import com.sample.scaffold.contract.dto.SingleDto;
import com.sample.scaffold.core.ServiceException;
import com.sample.scaffold.core.validate.annotation.ValidateAnno;
import com.sample.scaffold.model.Single;
import com.sample.scaffold.repository.SingleRepository;
import com.sample.scaffold.util.BeanMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SingleService implements ISingleService {

    @Autowired
    private SingleRepository singleRepository;

    @Override
    public SingleDto findOneSingle(Long id) throws Exception {
        if (id == null) {
            throw new ServiceException("id不允许为空");
        }
        Single single = singleRepository.findOne(id);
        SingleDto singleDto = BeanMapperUtil.getInstance().map(single, SingleDto.class);
        return singleDto;
    }

    @Override
    public void deleteSingle(Long id) throws Exception {
        if (id == null) {
            throw new ServiceException("id不允许为空");
        }
        singleRepository.delete(id);
    }

    @Override
    @ValidateAnno(cascadeValidate = true)
    public void saveSingle(SingleDto singleDto) throws Exception {
        Single single = BeanMapperUtil.getInstance().map(singleDto, Single.class);
        this.singleRepository.save(single);
    }
}
