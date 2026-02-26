package com.revhire.revhire.service.implementation;

import java.util.List;
import com.revhire.revhire.dao.CertificationDAO;
import com.revhire.revhire.dao.implementation.CertificationDAOImpl;
import com.revhire.revhire.modals.Certification;
import com.revhire.revhire.service.CertificationService;


public class CertificationServiceImpl implements CertificationService {

    private CertificationDAO certificationDAO = new CertificationDAOImpl();

    @Override
    public boolean addCertification(Certification certification) {

        // basic validation
        if (certification == null) {
            return false;
        }
        if (certification.getUserId() <= 0) {
            return false;
        }

        return certificationDAO.addCertification(certification);
    }

    @Override
    public List<Certification> getCertificationsByUserId(int userId) {

        if (userId <= 0) {
            return null;
        }

        return certificationDAO.getCertificationsByUserId(userId);
    }

    @Override
    public Certification getCertificationById(int certId) {

        if (certId <= 0) {
            return null;
        }

        return certificationDAO.getCertificationById(certId);
    }

    @Override
    public boolean updateCertification(Certification certification) {

        if (certification == null || certification.getCertId() <= 0) {
            return false;
        }

        return certificationDAO.updateCertification(certification);
    }

    @Override
    public boolean deleteCertification(int certId) {

        if (certId <= 0) {
            return false;
        }

        return certificationDAO.deleteCertification(certId);
    }
}
