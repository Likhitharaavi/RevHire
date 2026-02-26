package com.revhire.revhire.dao;

import java.util.List;
import com.revhire.revhire.modals.Certification;

public interface CertificationDAO {

    // add a new certification
    boolean addCertification(Certification certification);

    // get all certifications for a user
    List<Certification> getCertificationsByUserId(int userId);

    // get single certification by id
    Certification getCertificationById(int certId);

    // update certification details
    boolean updateCertification(Certification certification);

    // delete certification
    boolean deleteCertification(int certId);
}
