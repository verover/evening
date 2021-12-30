package com.enigmacamp.evening.util;

import com.enigmacamp.evening.entity.Role;
import com.enigmacamp.evening.entity.RoleName;
import com.enigmacamp.evening.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PageResponse<T> {
    List<T> data;
    private Long count;
    private  Integer totalPage;
    private Integer page;
    private Integer size;

    public PageResponse(List<T> data, Long count, Integer totalPage, Integer page, Integer size) {
        this.data = data;
        this.count = count;
        this.totalPage = totalPage;
        this.page = page;
        this.size = size;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    @Component
    public static class InitialDataLoader {

        @Autowired
        private RoleRepository roleRepository;

        @Bean
        public ApplicationRunner initial() {
            List<RoleName> roleList = Arrays.asList(RoleName.USER_ROLE, RoleName.ORGANIZER_ROLE, RoleName.ADMIN_ROLE);
            return args -> roleList.forEach(i -> createRoleIfNotFound(i));
        }

        private Optional<Role> createRoleIfNotFound(RoleName roleName) {
            Optional<Role> role = roleRepository.findByName(roleName);
                if (!role.isPresent()) {
                    Role newRole = new Role();
                    newRole.setName(roleName);
                    newRole = roleRepository.save(newRole);
                }
            return role;
        }
    }
}
