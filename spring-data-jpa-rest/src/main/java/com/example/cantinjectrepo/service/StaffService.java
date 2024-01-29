package com.example.cantinjectrepo.service;

import com.example.cantinjectrepo.domain.Staff;
import com.example.cantinjectrepo.repo.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {

    private final StaffRepository staffRepository;

    @Autowired
    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getStaffs() {
        return staffRepository.findAll();
    }

}