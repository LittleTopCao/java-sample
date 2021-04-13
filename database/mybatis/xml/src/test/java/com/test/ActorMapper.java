package com.test;

public interface ActorMapper {

    int insert(Actor actor);

    Actor selectById(Integer id);

    int deleteById(Integer id);

    int updateById(Actor actor);


}
