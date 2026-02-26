package com.revhire.revhire;

import java.sql.Date;
import java.util.List;

import com.revhire.revhire.modals.Certification;
import com.revhire.revhire.service.CertificationService;
import com.revhire.revhire.service.implementation.CertificationServiceImpl;

public class TestCertification {

    public static void main(String[] args) {

        // ✅ NO CASTING
        CertificationService service = new CertificationServiceImpl();

        Certification cert = new Certification();
        cert.setUserId(1);
        cert.setCertName("Java Full Stack");
        cert.setIssuingOrganization("Udemy");
        cert.setIssueDate(Date.valueOf("2024-01-10"));

        boolean added = service.addCertification(cert);
        System.out.println("Certification added: " + added);

        List<Certification> list = service.getCertificationsByUserId(1);

        if (list != null) {
            for (Certification c : list) {
                System.out.println(
                    c.getCertId() + " | " +
                    c.getCertName() + " | " +
                    c.getIssuingOrganization() + " | " +
                    c.getIssueDate()
                );
            }
        }
    }
}

