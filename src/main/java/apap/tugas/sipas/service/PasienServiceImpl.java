package apap.tugas.sipas.service;

import apap.tugas.sipas.model.AsuransiModel;
import apap.tugas.sipas.model.DiagnosisPenyakitModel;
import apap.tugas.sipas.model.EmergencyContactModel;
import apap.tugas.sipas.model.PasienModel;
import apap.tugas.sipas.repository.AsuransiDB;
import apap.tugas.sipas.repository.DiagnosisPenyakitDB;
import apap.tugas.sipas.repository.EmergencyContactDB;
import apap.tugas.sipas.repository.PasienDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PasienServiceImpl implements PasienService {
    @Autowired
    private PasienDB pasienDB;

    @Autowired
    private EmergencyContactDB emergencyContactDB;

    @Autowired
    private AsuransiDB asuransiDB;

    @Override
    public void addPasien(PasienModel pasienModel) {
        pasienDB.save(pasienModel);
    }

    @Override
    public void addEmergencyContact(EmergencyContactModel emergencyContactModel) {
        emergencyContactDB.save(emergencyContactModel);
    }

    @Override
    public List<PasienModel> getPasienList() {
        return pasienDB.findAll();
    }

    @Override
    public EmergencyContactModel getEmergencyContactModel() {
        return null;
    }

    @Override
    public Optional<PasienModel> getPasiendByIdPasien(Long idPasien) {
        return pasienDB.findByIdPasien(idPasien);
    }

    @Override
    public Optional<PasienModel> getPasienByNIKPasien(Long nikPasien) {
        return pasienDB.findByNikPasien(nikPasien);
    }

    @Override
    public List<AsuransiModel> getAsuransiList() {
        return asuransiDB.findAll();
    }

    @Override
    public List<DiagnosisPenyakitModel> getDiagnosisPenyakitList() {
        return null;
    }

    @Override
    public PasienModel changeRestoran(PasienModel pasienModel) {
        PasienModel targetPasien = pasienDB.findByNikPasien(pasienModel.getNikPasien()).get();
        EmergencyContactModel targetEC = pasienDB.findByNikPasien(pasienModel.getNikPasien()).get().getEmergencyContactModel();
        try {
            targetPasien.setNamaPasien(pasienModel.getNamaPasien());
            targetPasien.setNikPasien(pasienModel.getNikPasien());
            targetPasien.setJenisKelamin(pasienModel.getJenisKelamin());
            targetPasien.setTanggalLahir(pasienModel.getTanggalLahir());
            targetPasien.setTempatLahir(pasienModel.getTempatLahir());
            targetEC.setNamaEC(pasienModel.getEmergencyContactModel().getNamaEC());
            targetEC.setNikEC(pasienModel.getEmergencyContactModel().getNikEC());
            targetEC.setHpEC(pasienModel.getEmergencyContactModel().getHpEC());
            pasienDB.save(targetPasien);
            emergencyContactDB.save(targetEC);
            return targetPasien;
        } catch (NullPointerException nullException) {
            return null;
        }
    }

    @Override
    public void deletePasien(PasienModel pasienModel) {
        pasienDB.delete(pasienModel);
    }


}
