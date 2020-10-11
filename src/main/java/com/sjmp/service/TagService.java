package com.sjmp.service;

import com.sjmp.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface TagService {
//    新增完，返回一个 Tag
    Tag saveTag(Tag tag);
//    按 id 查询 Tag
    Tag getTag(Long id);
//    按页查询
    Page<Tag> listTag(Pageable pageable);
//    更新
    Tag updateTag(Long id, Tag tag);
//    删除
    void deleteTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag();

    List<Tag> listTagTop(Integer size);

    List<Tag> listTag(String ids);

}
