package com.revhire.revhire.service;

import java.util.List;
import com.revhire.revhire.modals.Certification;

public interface CertificationService {

    boolean addCertification(Certification certification);

    List<Certification> getCertificationsByUserId(int userId);

    Certification getCertificationById(int certId);

    boolean updateCertification(Certification certification);

    boolean deleteCertification(int certId);
}
