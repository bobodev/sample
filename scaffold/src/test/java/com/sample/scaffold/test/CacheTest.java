package com.sample.scaffold.test;

import com.sample.scaffold.Application;
import com.sample.scaffold.model.User;
import com.sample.scaffold.service.biz.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class CacheTest {

    @Autowired
    private IUserService userService;

    @Test
    public void CacheableTest() throws Exception {
        System.out.println("第一次获取，获取真实数据");
        User oneUser1 = userService.findOneUser(4l);
        String nameBefore = oneUser1.getName();
        System.out.println("第二次获取，获取缓存数据");
        User oneUser2 = userService.findOneUser(4l);
        String nameAfter = oneUser2.getName();
        System.out.println("nameBefore.equals(nameAfter) = " + nameBefore.equals(nameAfter));
        Assert.assertEquals(nameBefore,nameAfter);
    }

    @Test
    public void CacheEvictTest() throws Exception{
        User oneUser1 = userService.findOneUser(4l);
        String nameBefore = oneUser1.getName();
        System.out.println("清除缓存");
        userService.deleteUser(4L);
        User oneUser2 = userService.findOneUser(4l);
        String nameAfter = oneUser2.getName();
        Assert.assertNotEquals(nameBefore,nameAfter);
    }

    @Test
    public void CachePutTest() throws Exception{
        User oneUser1 = userService.findOneUser(4l);
        String addressBefore = oneUser1.getContactAddress();
        System.out.println("更新缓存");
        oneUser1.setContactAddress("江苏省");
        userService.updateUser(oneUser1);
        User oneUser2 = userService.findOneUser(4l);
        String addressAfter = oneUser2.getContactAddress();
        Assert.assertNotEquals(addressBefore,addressAfter);
    }

    @Test
    public void CacheEvictAllTest() throws Exception{
        User oneUser1 = userService.findOneUser(4l);
        String nameBefore = oneUser1.getName();
        System.out.println("清除缓存");
        userService.deleteAllCache();
        User oneUser2 = userService.findOneUser(4l);
        String nameAfter = oneUser2.getName();
        Assert.assertNotEquals(nameBefore,nameAfter);
    }


}
