/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.noad.solutions.tis.ejb.sessionbeans;

import com.noad.solutions.tis.ejb.entities.AcademicLevel;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.AccessPage;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.Department;
import com.noad.solutions.tis.ejb.entities.Ebo;
import com.noad.solutions.tis.ejb.entities.GenId;
import com.noad.solutions.tis.ejb.entities.GenIdPK;
import com.noad.solutions.tis.ejb.entities.Guardiance;
import com.noad.solutions.tis.ejb.entities.IncompleteCourse;
import com.noad.solutions.tis.ejb.entities.LecturerTeachingCourse;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.ProgramCourse;
import com.noad.solutions.tis.ejb.entities.SchoolBill;
import com.noad.solutions.tis.ejb.entities.SchoolBillCategory;
import com.noad.solutions.tis.ejb.entities.SchoolBillItem;
import com.noad.solutions.tis.ejb.entities.SchoolSetup;
import com.noad.solutions.tis.ejb.entities.SemesterRegistration;
import com.noad.solutions.tis.ejb.entities.Staff;
import com.noad.solutions.tis.ejb.entities.StaffQualification;
import com.noad.solutions.tis.ejb.entities.Student;
import com.noad.solutions.tis.ejb.entities.StudentAccount;
import com.noad.solutions.tis.ejb.entities.StudentLevel;
import com.noad.solutions.tis.ejb.entities.StudentMark;
import com.noad.solutions.tis.ejb.entities.UserAccount;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;

/**
 *
 * @author daud
 */
@Stateless
@LocalBean
public class TisCrudSessionbean {

    @PersistenceContext(unitName = "tis-EJBPU")
    private EntityManager em;
    private String lastActivityExceptionMessage;
    private Exception lastActivityException;

    // <editor-fold defaultstate="collapsed" desc=" GenId CRUD">
    public GenIdPK genIdCreate(GenId genId) {
        try {

            em.persist(genId);
            em.flush();
            return genId.getGenIdPK();

        } catch (Exception e) {
            appLogger(e);
            return null;

        }

    }

    public GenIdPK genIdCreate(GenIdPK genIdPK) {
        try {
            GenId gen = new GenId(genIdPK);
            gen.setId(1);
            gen.setIdBig(BigInteger.ONE);
            em.persist(gen);
            em.flush();
            return gen.getGenIdPK();

        } catch (Exception e) {
            appLogger(e);
            return null;

        }

    }

    public boolean genIdDelete(GenId GenId) {
        try {


            em.remove(em.merge(GenId));
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean genIdUpdate(GenId GenId) {
        try {

            em.merge(GenId);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public GenId genIdFind(GenIdPK GenIdPK) {
        try {

            GenId GenId = em.find(GenId.class, GenIdPK);
            return GenId;
        } catch (Exception e) {
            return null;
        }
    }

    public List<GenId> genIdFindByAttribute(String GenIdAttribute, Object attributeValue, String fieldType) {
        List<GenId> listOfGenId = null;

        String qryString = "";

        qryString = "SELECT e FROM GenId e ";
        qryString += "WHERE e." + GenIdAttribute + " =:GenIdAttribute ";

        try {
            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfGenId = (List<GenId>) em.createQuery(qryString).setParameter("GenIdAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                listOfGenId = (List<GenId>) em.createQuery(qryString).setParameter("GenIdAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfGenId = (List<GenId>) em.createQuery(qryString).setParameter("GenIdAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfGenId;

        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }

    public List<GenId> genIdGetAll() {
        List<GenId> listOfGenId = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM GenId e";


            listOfGenId = (List<GenId>) em.createQuery(qryString).getResultList();

            return listOfGenId;

        } catch (Exception e) {
            appLogger(e);
        }
        return null;
    }

    public GenId genIdGetNextId(GenIdPK genIdPK) {
        try {

            GenId gen = genIdFind(genIdPK);
            System.out.println("Gen Found: " + gen.getGenIdPK().getPrimaryKey());
            GenId detail = getGenidNextId(gen);

            em.merge(detail);
            em.flush();

            return detail;
        } catch (Exception e) {
            GenIdPK gPK = genIdCreate(genIdPK);
            System.out.println("GPK : " + gPK);
            return genIdFind(gPK);
        }


    }

    public GenId genIdGetNextId(String primkey, String extraInfo, String application) {
        if (extraInfo.isEmpty()) {
            extraInfo = "0";
        }
        System.out.println("GPK " + primkey + " e " + extraInfo + " ap " + application);
        GenIdPK genIdPK = new GenIdPK(primkey, extraInfo, application);
        return genIdGetNextId(genIdPK);
    }

    public GenId getGenidNextId(GenId gen) {
        Integer nextid = gen.getId() + 1;
        //System.out.println("Genid generateidnextid method next genid : "+nextid);
        //  GenId detail = new GenId();
        gen.setId(nextid);
        return gen;
    }

    public GenId getGenidNextIdb(GenId gen) {
        BigInteger nextid = gen.getIdBig().add(new BigInteger("1"));
        //System.out.println("Genid getGenidNextIdb method next genidb : "+nextid);
        GenId detail = new GenId();
        detail.setId(0);
        detail.setIdBig(nextid);
        detail.setGenIdPK(gen.getGenIdPK());

        return detail;
    }

    public GenId genIdGetNextIdbigint(String primkey, String extraInfo, String application) {
        ////System.out.println("GenIdGetNextIdb primkey = " + primkey);
        try {
            GenIdPK genIdPk = new GenIdPK(primkey, extraInfo, application);
            GenId gen = em.find(GenId.class, genIdPk);
            GenId g = getGenidNextIdb(gen);
            em.merge(g);
            em.flush();
            return g;
        } catch (Exception e) {
        }

        return null;


    }

    public String genIdGetNextIdString(String primkey, String extraInfo, String application, String formatPattern) {
        ////System.out.println("GenIdGetNextIdString primkey = " + primkey);
        try {
            // Example: formatPattern = "00"
            DecimalFormat myFormatter = new DecimalFormat(formatPattern);

            GenId gd = genIdGetNextId(primkey, extraInfo, application);


            return myFormatter.format(gd.getId());


        } catch (Exception e) {
            return null;
            // throw new Exception("GenIdGetNextIdString exception: " + e);


        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" AcademicLevel CRUD">

    public String academicLevelCreate(AcademicLevel academicLevel) {
        try {

            academicLevel.setRemoved("NO");
            academicLevel.setCreatedDate(new Date());
            em.persist(academicLevel);
            em.flush();
            return academicLevel.getAcademicLevel();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean academicLevelDelete(AcademicLevel academicLevel, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(academicLevel));
            } else if (permanent == false) {
                academicLevel.setRemoved("YES");
                academicLevel.setEditedDate(new Date());
                em.merge(academicLevel);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean academicLevelUpdate(AcademicLevel academicLevel) {
        try {

            academicLevel.setRemoved("NO");
            academicLevel.setEditedDate(new Date());
            em.merge(academicLevel);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public AcademicLevel academicLevelFind(String academicLevelId) {
        try {

            AcademicLevel academicLevel = em.find(AcademicLevel.class, academicLevelId);
            return academicLevel;
        } catch (Exception e) {
            return null;
        }
    }

    public List<AcademicLevel> academicLevelFindByAttribute(String academicLevelAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<AcademicLevel> listOfSemesterRegistrations = null;

        String qryString = "";

        qryString = "SELECT e FROM AcademicLevel e ";
        qryString += "WHERE e." + academicLevelAttribute + " =:academicLevelAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSemesterRegistrations = (List<AcademicLevel>) em.createQuery(qryString).setParameter("academicLevelAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM AcademicLevel e ";
                qryString += "WHERE e." + academicLevelAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSemesterRegistrations = (List<AcademicLevel>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSemesterRegistrations = (List<AcademicLevel>) em.createQuery(qryString).setParameter("academicLevelAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<AcademicLevel>();
    }

    public List<AcademicLevel> academicLevelGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<AcademicLevel> listOfSemesterRegistrations = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicLevel e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicLevel e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfSemesterRegistrations = (List<AcademicLevel>) em.createQuery(qryString).getResultList();

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<AcademicLevel>();
    }

    public List<AcademicLevel> academicLevelGetAll(boolean includeLogicallyDeleted) {
        List<AcademicLevel> listOfAcademicLevels = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicLevel e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicLevel e WHERE e.removed = 'NO'";
            }

            listOfAcademicLevels = (List<AcademicLevel>) em.createQuery(qryString).getResultList();

            return listOfAcademicLevels;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<AcademicLevel>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" AcademicYear CRUD">

    public String academicYearCreate(AcademicYear academicYear) {
        try {

            academicYear.setRemoved("NO");
            academicYear.setCreatedDate(new Date());
            em.persist(academicYear);
            em.flush();
            return academicYear.getAcademicYearId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean academicYearDelete(AcademicYear academicYear, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(academicYear));
            } else if (permanent == false) {
                academicYear.setRemoved("YES");
                academicYear.setEditedDate(new Date());
                em.merge(academicYear);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean academicYearUpdate(AcademicYear academicYear) {
        try {

            academicYear.setRemoved("NO");
            academicYear.setEditedDate(new Date());
            em.merge(academicYear);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public AcademicYear academicYearFind(String academicYearId) {
        try {

            AcademicYear academicYear = em.find(AcademicYear.class, academicYearId);
            return academicYear;
        } catch (Exception e) {
            return null;
        }
    }

    public List<AcademicYear> academicYearFindByAttribute(String academicYearAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<AcademicYear> listOfAcademicYear = null;

        String qryString = "";

        qryString = "SELECT e FROM AcademicYear e ";
        qryString += "WHERE e." + academicYearAttribute + " =:academicYearAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).setParameter("academicYearAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM AcademicYear e ";
                qryString += "WHERE e." + academicYearAttribute + " LIKE '%" + attributeValue + "%'";
                listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).setParameter("academicYearAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfAcademicYear;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<AcademicYear>();
    }

    public List<AcademicYear> academicYearGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<AcademicYear> listOfAcademicYear = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicYear e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicYear e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).getResultList();

            return listOfAcademicYear;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<AcademicYear>();
    }

    public List<AcademicYear> academicYearGetAll(boolean includeLogicallyDeleted) {
        List<AcademicYear> listOfAcademicYear = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM AcademicYear e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM AcademicYear e WHERE e.removed = 'NO'";
            }

            listOfAcademicYear = (List<AcademicYear>) em.createQuery(qryString).getResultList();

            return listOfAcademicYear;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<AcademicYear>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Course CRUD">
    public String courseCreate(Course course) {
        try {
            course.setCreatedDate(new Date());
            course.setRemoved("NO");
            em.persist(course);
            em.flush();
            return course.getCourseId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean courseDelete(Course course, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(course));
            } else if (permanent == false) {
                course.setRemoved("YES");
                course.setEditedDate(new Date());
                em.merge(course);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean courseUpdate(Course course) {
        try {
            course.setEditedDate(new Date());
            course.setRemoved("NO");
            em.merge(course);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public Course courseFind(String courseId) {
        try {

            Course course = em.find(Course.class, courseId);
            return course;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Course> courseFindByAttribute(String courseAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Course> listOfCourse = null;

        String qryString = "";

        qryString = "SELECT e FROM Course e ";
        qryString += "WHERE e." + courseAttribute + " =:courseAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfCourse = (List<Course>) em.createQuery(qryString).setParameter("courseAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Course e ";
                qryString += "WHERE e." + courseAttribute + " LIKE '%" + attributeValue + "%'";
                listOfCourse = (List<Course>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfCourse = (List<Course>) em.createQuery(qryString).setParameter("courseAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Course>();
    }

    public List<Course> courseGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<Course> listOfCourse = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Course e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Course e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfCourse = (List<Course>) em.createQuery(qryString).getResultList();

            return listOfCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Course>();
    }

    public List<Course> courseGetAll(boolean includeLogicallyDeleted) {
        List<Course> listOfCourse = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Course e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Course e WHERE e.removed = 'NO'";
            }

            listOfCourse = (List<Course>) em.createQuery(qryString).getResultList();

            return listOfCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Course>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Department CRUD">

    public String departmentCreate(Department department) {
        try {

            department.setRemoved("NO");
            department.setCreatedDate(new Date());
            em.persist(department);
            em.flush();
            return department.getDepartmentId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean departmentDelete(Department department, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(department));
            } else if (permanent == false) {
                department.setRemoved("YES");
                department.setEditedDate(new Date());
                em.merge(department);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean departmentUpdate(Department department) {
        try {
            department.setEditedDate(new Date());
            department.setRemoved("NO");
            em.merge(department);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public Department departmentFind(String departmentId) {
        try {

            Department department = em.find(Department.class, departmentId);
            return department;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Department> departmentFindByAttribute(String departmentAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Department> listOfDepartment = null;

        String qryString = "";

        qryString = "SELECT e FROM Department e ";
        qryString += "WHERE e." + departmentAttribute + " =:departmentAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfDepartment = (List<Department>) em.createQuery(qryString).setParameter("departmentAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Department e ";
                qryString += "WHERE e." + departmentAttribute + " LIKE '%" + attributeValue + "%'";
                listOfDepartment = (List<Department>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfDepartment = (List<Department>) em.createQuery(qryString).setParameter("departmentAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfDepartment;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Department>();
    }

    public List<Department> departmentGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<Department> listOfDepartment = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Department e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Department e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfDepartment = (List<Department>) em.createQuery(qryString).getResultList();

            return listOfDepartment;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Department>();
    }

    public List<Department> departmentGetAll(boolean includeLogicallyDeleted) {
        List<Department> listOfDepartment = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Department e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Department e WHERE e.removed = 'NO'";
            }

            listOfDepartment = (List<Department>) em.createQuery(qryString).getResultList();

            return listOfDepartment;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Department>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Guardiance CRUD">
    public String guardianceCreate(Guardiance guardiance) {
        try {

            guardiance.setRemoved("NO");
            guardiance.setCreatedDate(new Date());
            em.persist(guardiance);
            em.flush();
            return guardiance.getGuardianceId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean guardianceDelete(Guardiance guardiance, boolean permanent) {
        try {
            if (permanent == true) {
                em.remove(em.merge(guardiance));
            } else if (permanent == false) {
                guardiance.setEditedDate(new Date());
                guardiance.setRemoved("YES");
                em.merge(guardiance);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean guardianceUpdate(Guardiance guardiance) {
        try {
            guardiance.setEditedDate(new Date());
            guardiance.setRemoved("NO");
            em.merge(guardiance);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public Guardiance guardianceFind(String guardianceId) {
        try {

            Guardiance guardiance = em.find(Guardiance.class, guardianceId);
            return guardiance;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Guardiance> guardianceFindByAttribute(String guardianceAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Guardiance> listOfGuardiance = null;

        String qryString = "";

        qryString = "SELECT e FROM Guardiance e ";
        qryString += "WHERE e." + guardianceAttribute + " =:guardianceAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfGuardiance = (List<Guardiance>) em.createQuery(qryString).setParameter("guardianceAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Guardiance e ";
                qryString += "WHERE e." + guardianceAttribute + " LIKE '%" + attributeValue + "%'";
                listOfGuardiance = (List<Guardiance>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfGuardiance = (List<Guardiance>) em.createQuery(qryString).setParameter("guardianceAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfGuardiance;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Guardiance>();
    }

    public List<Guardiance> guardianceGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<Guardiance> listOfGuardiance = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Guardiance e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Guardiance e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfGuardiance = (List<Guardiance>) em.createQuery(qryString).getResultList();

            return listOfGuardiance;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Guardiance>();
    }

    public List<Guardiance> guardianceGetAll(boolean includeLogicallyDeleted) {
        List<Guardiance> listOfGuardiance = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Guardiance e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Guardiance e WHERE e.removed = 'NO'";
            }

            listOfGuardiance = (List<Guardiance>) em.createQuery(qryString).getResultList();

            return listOfGuardiance;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Guardiance>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" IncompleteCourse CRUD">

    public String incompleteCourseCreate(IncompleteCourse incompleteCourse) {
        try {

            incompleteCourse.setRemoved("NO");
//            incompleteCourse.setCreatedDate(new Date());
            em.persist(incompleteCourse);
            em.flush();
            return incompleteCourse.getIncompleteCourseId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean incompleteCourseDelete(IncompleteCourse incompleteCourse, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(incompleteCourse));
            } else if (permanent == false) {
                incompleteCourse.setRemoved("YES");
//                incompleteCourse.setEditedDate(new Date());
                em.merge(incompleteCourse);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean incompleteCourseUpdate(IncompleteCourse incompleteCourse) {
        try {

            incompleteCourse.setRemoved("NO");
//            incompleteCourse.setEditedDate(new Date());

            em.merge(incompleteCourse);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public IncompleteCourse incompleteCourseFind(String incompleteCourseId) {
        try {

            IncompleteCourse incompleteCourse = em.find(IncompleteCourse.class, incompleteCourseId);
            return incompleteCourse;
        } catch (Exception e) {
            return null;
        }
    }

    public List<IncompleteCourse> incompleteCourseFindByAttribute(String incompleteCourseAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<IncompleteCourse> listOfIncompleteCourse = null;

        String qryString = "";

        qryString = "SELECT e FROM IncompleteCourse e ";
        qryString += "WHERE e." + incompleteCourseAttribute + " =:incompleteCourseAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfIncompleteCourse = (List<IncompleteCourse>) em.createQuery(qryString).setParameter("incompleteCourseAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM IncompleteCourse e ";
                qryString += "WHERE e." + incompleteCourseAttribute + " LIKE '%" + attributeValue + "%'";
                listOfIncompleteCourse = (List<IncompleteCourse>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfIncompleteCourse = (List<IncompleteCourse>) em.createQuery(qryString).setParameter("incompleteCourseAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfIncompleteCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<IncompleteCourse>();
    }

    public List<IncompleteCourse> incompleteCourseGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<IncompleteCourse> listOfIncompleteCourse = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM IncompleteCourse e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM IncompleteCourse e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfIncompleteCourse = (List<IncompleteCourse>) em.createQuery(qryString).getResultList();

            return listOfIncompleteCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<IncompleteCourse>();
    }

    public List<IncompleteCourse> incompleteCourseGetAll(boolean includeLogicallyDeleted) {
        List<IncompleteCourse> listOfIncompleteCourse = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM IncompleteCourse e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM IncompleteCourse e WHERE e.removed = 'NO'";
            }

            listOfIncompleteCourse = (List<IncompleteCourse>) em.createQuery(qryString).getResultList();

            return listOfIncompleteCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<IncompleteCourse>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Program CRUD">

    public String programCreate(Program program) {
        try {

            program.setRemoved("NO");
            program.setCreatedDate(new Date());
            em.persist(program);
            em.flush();
            return program.getProgramId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean programDelete(Program program, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(program));
            } else if (permanent == false) {
                program.setRemoved("YES");
                program.setEditedDate(new Date());
                em.merge(program);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean programUpdate(Program program) {
        try {

            program.setRemoved("NO");
            program.setEditedDate(new Date());

            em.merge(program);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public Program programFind(String programId) {
        try {

            Program program = em.find(Program.class, programId);
            return program;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Program> programFindByAttribute(String programAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Program> listOfProgram = null;

        String qryString = "";

        qryString = "SELECT e FROM Program e ";
        qryString += "WHERE e." + programAttribute + " =:programAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfProgram = (List<Program>) em.createQuery(qryString).setParameter("programAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Program e ";
                qryString += "WHERE e." + programAttribute + " LIKE '%" + attributeValue + "%'";
                listOfProgram = (List<Program>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfProgram = (List<Program>) em.createQuery(qryString).setParameter("programAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfProgram;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Program>();
    }

    public List<Program> programGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<Program> listOfProgram = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Program e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Program e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfProgram = (List<Program>) em.createQuery(qryString).getResultList();

            return listOfProgram;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Program>();
    }

    public List<Program> programGetAll(boolean includeLogicallyDeleted) {
        List<Program> listOfProgram = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Program e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Program e WHERE e.removed = 'NO'";
            }

            listOfProgram = (List<Program>) em.createQuery(qryString).getResultList();

            return listOfProgram;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Program>();
    }
// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" ProgramCourse CRUD">

    public String programCourseCreate(ProgramCourse programCourse) {
        try {

            programCourse.setRemoved("NO");
            programCourse.setCreatedDate(new Date());
            em.persist(programCourse);
            em.flush();
            return programCourse.getProgramCourseId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean programCourseDelete(ProgramCourse programCourse, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(programCourse));
            } else if (permanent == false) {
                programCourse.setRemoved("YES");
                programCourse.setEditedDate(new Date());
                em.merge(programCourse);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean programCourseUpdate(ProgramCourse programCourse) {
        try {

            programCourse.setRemoved("NO");
            programCourse.setEditedDate(new Date());

            em.merge(programCourse);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public ProgramCourse programCourseFind(String programCourseId) {
        try {

            ProgramCourse programCourse = em.find(ProgramCourse.class, programCourseId);
            return programCourse;
        } catch (Exception e) {
            return null;
        }
    }

    public List<ProgramCourse> programCourseFindByAttribute(String programCourseAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<ProgramCourse> listOfProgramCourse = null;

        String qryString = "";

        qryString = "SELECT e FROM ProgramCourse e ";
        qryString += "WHERE e." + programCourseAttribute + " =:programCourseAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfProgramCourse = (List<ProgramCourse>) em.createQuery(qryString).setParameter("programCourseAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM ProgramCourse e ";
                qryString += "WHERE e." + programCourseAttribute + " LIKE '%" + attributeValue + "%'";
                listOfProgramCourse = (List<ProgramCourse>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfProgramCourse = (List<ProgramCourse>) em.createQuery(qryString).setParameter("programCourseAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfProgramCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<ProgramCourse>();
    }

    public List<ProgramCourse> programCourseGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<ProgramCourse> listOfProgramCourse = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ProgramCourse e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ProgramCourse e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfProgramCourse = (List<ProgramCourse>) em.createQuery(qryString).getResultList();

            return listOfProgramCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<ProgramCourse>();
    }

    public List<ProgramCourse> programCourseGetAll(boolean includeLogicallyDeleted) {
        List<ProgramCourse> listOfProgramCourse = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM ProgramCourse e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM ProgramCourse e WHERE e.removed = 'NO'";
            }

            listOfProgramCourse = (List<ProgramCourse>) em.createQuery(qryString).getResultList();

            return listOfProgramCourse;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<ProgramCourse>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" LecturerTeachingCourse CRUD">

    public String lecturerTeachingCourseCreate(LecturerTeachingCourse lecturerTeachingCourse) {
        try {

            lecturerTeachingCourse.setRemoved("NO");
            lecturerTeachingCourse.setCreatedDate(new Date());
            em.persist(lecturerTeachingCourse);
            em.flush();
            return lecturerTeachingCourse.getLecturerCourseId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean lecturerTeachingCourseDelete(LecturerTeachingCourse lecturerTeachingCourse, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(lecturerTeachingCourse));
            } else if (permanent == false) {
                lecturerTeachingCourse.setRemoved("YES");
                lecturerTeachingCourse.setEditedDate(new Date());
                em.merge(lecturerTeachingCourse);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean lecturerTeachingCourseUpdate(LecturerTeachingCourse lecturerTeachingCourse) {
        try {

            lecturerTeachingCourse.setRemoved("NO");
            lecturerTeachingCourse.setEditedDate(new Date());
            em.merge(lecturerTeachingCourse);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public LecturerTeachingCourse lecturerTeachingCourseFind(String lecturerTeachingCourseId) {
        try {

            LecturerTeachingCourse lecturerTeachingCourse = em.find(LecturerTeachingCourse.class, lecturerTeachingCourseId);
            return lecturerTeachingCourse;
        } catch (Exception e) {
            return null;
        }
    }

    public List<LecturerTeachingCourse> lecturerTeachingCourseFindByAttribute(String lecturerTeachingCourseAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<LecturerTeachingCourse> listOfSemesterRegistrations = null;

        String qryString = "";

        qryString = "SELECT e FROM LecturerTeachingCourse e ";
        qryString += "WHERE e." + lecturerTeachingCourseAttribute + " =:lecturerTeachingCourseAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSemesterRegistrations = (List<LecturerTeachingCourse>) em.createQuery(qryString).setParameter("lecturerTeachingCourseAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM LecturerTeachingCourse e ";
                qryString += "WHERE e." + lecturerTeachingCourseAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSemesterRegistrations = (List<LecturerTeachingCourse>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSemesterRegistrations = (List<LecturerTeachingCourse>) em.createQuery(qryString).setParameter("lecturerTeachingCourseAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<LecturerTeachingCourse>();
    }

    public List<LecturerTeachingCourse> lecturerTeachingCourseGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<LecturerTeachingCourse> listOfSemesterRegistrations = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM LecturerTeachingCourse e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM LecturerTeachingCourse e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfSemesterRegistrations = (List<LecturerTeachingCourse>) em.createQuery(qryString).getResultList();

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<LecturerTeachingCourse>();
    }

    public List<LecturerTeachingCourse> lecturerTeachingCourseGetAll(boolean includeLogicallyDeleted) {
        List<LecturerTeachingCourse> listOfLecturerTeachingCourses = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM LecturerTeachingCourse e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM LecturerTeachingCourse e WHERE e.removed = 'NO'";
            }

            listOfLecturerTeachingCourses = (List<LecturerTeachingCourse>) em.createQuery(qryString).getResultList();

            return listOfLecturerTeachingCourses;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<LecturerTeachingCourse>();
    }
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" SchoolBill CRUD">

    public String schoolBillCreate(SchoolBill schoolBill) {
        try {

            em.persist(schoolBill);
            em.flush();
            return schoolBill.getSchoolBillId();
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public boolean schoolBillDelete(SchoolBill schoolBill) {
        try {
            em.remove(em.merge(schoolBill));
            em.flush();
            return true;
        } catch (Exception e) {
            appLogger(e);
            return false;
        }
    }

    public boolean schoolBillUpdate(SchoolBill schoolBill) {
        try {

            em.merge(schoolBill);
            em.flush();
            return true;
        } catch (Exception e) {
            appLogger(e);
            return false;
        }
    }

    public SchoolBill schoolBillFindById(String id) {
        try {
            return (SchoolBill) em.find(SchoolBill.class, id);
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public List<SchoolBill> schoolBillGetAll() {
        try {
            String query = "SELECT e FROM SchoolBill e";

            return em.createQuery(query).getResultList();
        } catch (Exception e) {
            Logger.getLogger(TisCrudSessionbean.class.getName()).log(Level.SEVERE, null, e.getCause());
            return null;
        }
    }

    public List<SchoolBill> schoolBillFindByAttribute(String parameter, Object paramValue, String paramType) {
        try {
            List<SchoolBill> listOfSchoolBills = null;
            String query = "SELECT e FROM SchoolBill e WHERE e.paramter LIKE :paramValue;";
            listOfSchoolBills = em.createQuery(query).setParameter("paramValue", paramValue).getResultList();
            return listOfSchoolBills;
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SchoolBillCategory CRUD">

    public String schoolBillCategoryCreate(SchoolBillCategory schoolBillCategory) {
        try {

//            schoolBillCategory.setRemoved("NO");
            schoolBillCategory.setCreatedDate(new Date());
            em.persist(schoolBillCategory);
            em.flush();
            return schoolBillCategory.getSchoolBillCategoryId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean schoolBillCategoryDelete(SchoolBillCategory schoolBillCategory, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(schoolBillCategory));
            } else if (permanent == false) {
//                schoolBillCategory.setRemoved("YES");
                schoolBillCategory.setEditedDate(new Date());
                em.merge(schoolBillCategory);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean schoolBillCategoryUpdate(SchoolBillCategory schoolBillCategory) {
        try {

//            schoolBillCategory.setRemoved("NO");
            schoolBillCategory.setEditedDate(new Date());

            em.merge(schoolBillCategory);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public SchoolBillCategory schoolBillCategoryFind(String schoolBillCategoryId) {
        try {

            SchoolBillCategory schoolBillCategory = em.find(SchoolBillCategory.class, schoolBillCategoryId);
            return schoolBillCategory;
        } catch (Exception e) {
            return null;
        }
    }

    public List<SchoolBillCategory> schoolBillCategoryFindByAttribute(String schoolBillCategoryAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SchoolBillCategory> listOfSchoolBillCategory = null;

        String qryString = "";

        qryString = "SELECT e FROM SchoolBillCategory e ";
        qryString += "WHERE e." + schoolBillCategoryAttribute + " =:schoolBillCategoryAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSchoolBillCategory = (List<SchoolBillCategory>) em.createQuery(qryString).setParameter("schoolBillCategoryAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SchoolBillCategory e ";
                qryString += "WHERE e." + schoolBillCategoryAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSchoolBillCategory = (List<SchoolBillCategory>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSchoolBillCategory = (List<SchoolBillCategory>) em.createQuery(qryString).setParameter("schoolBillCategoryAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSchoolBillCategory;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<SchoolBillCategory>();
    }

    public List<SchoolBillCategory> schoolBillCategoryGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<SchoolBillCategory> listOfSchoolBillCategory = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolBillCategory e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolBillCategory e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfSchoolBillCategory = (List<SchoolBillCategory>) em.createQuery(qryString).getResultList();

            return listOfSchoolBillCategory;
        } catch (Exception e) {
            Logger.getLogger(TisCrudSessionbean.class.getName()).log(Level.SEVERE, null, e.getCause());
        }
        return new ArrayList<SchoolBillCategory>();
    }

    public List<SchoolBillCategory> schoolBillCategoryGetAll(boolean includeLogicallyDeleted) {
        List<SchoolBillCategory> listOfSchoolBillCategory = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolBillCategory e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolBillCategory e WHERE e.removed = 'NO'";
            }

            listOfSchoolBillCategory = (List<SchoolBillCategory>) em.createQuery(qryString).getResultList();

            return listOfSchoolBillCategory;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<SchoolBillCategory>();
    }
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" SchoolBillItem CRUD">

    public String schoolBillItemCreate(SchoolBillItem schoolBillItem) {
        try {

            em.persist(schoolBillItem);
            em.flush();
            return schoolBillItem.getSchoolBillItemId();
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public boolean schoolBillItemDelete(SchoolBillItem schoolBillItem) {
        try {
            em.remove(em.merge(schoolBillItem));
            em.flush();
            return true;
        } catch (Exception e) {
            appLogger(e);
            return false;
        }
    }

    public boolean schoolBillItemUpdate(SchoolBillItem schoolBillItem) {
        try {

            em.merge(schoolBillItem);
            em.flush();
            return true;
        } catch (Exception e) {
            appLogger(e);
            return false;
        }
    }

    public SchoolBillItem schoolBillItemFindById(String id) {
        try {
            return (SchoolBillItem) em.find(SchoolBillItem.class, id);
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public List<SchoolBillItem> schoolBillItemGetAll() {
        try {
            String query = "SELECT e FROM SchoolBillItem e";

            return em.createQuery(query).getResultList();
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public List<SchoolBillItem> schoolBillItemFindByAttribute(String parameter, Object paramValue, String paramType) {
        try {
            List<SchoolBillItem> listOfSchoolBillItems = null;
            String query = "SELECT e FROM SchoolBillItem e WHERE e.paramter LIKE :paramValue;";
            listOfSchoolBillItems = em.createQuery(query).setParameter("paramValue", paramValue).getResultList();
            return listOfSchoolBillItems;
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" SchoolSetup CRUD">

    public String schoolSetupCreate(SchoolSetup schoolSetup) {
        try {

            schoolSetup.setRemoved("NO");
            schoolSetup.setCreatedDate(new Date());
            em.persist(schoolSetup);
            em.flush();
            return schoolSetup.getSchoolSetupId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean schoolSetupDelete(SchoolSetup schoolSetup, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(schoolSetup));
            } else if (permanent == false) {
                schoolSetup.setRemoved("YES");
                schoolSetup.setEditedDate(new Date());
                em.merge(schoolSetup);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean schoolSetupUpdate(SchoolSetup schoolSetup) {
        try {

            schoolSetup.setRemoved("NO");
            schoolSetup.setEditedDate(new Date());

            em.merge(schoolSetup);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public SchoolSetup schoolSetupFind(String schoolSetupId) {
        try {

            SchoolSetup schoolSetup = em.find(SchoolSetup.class, schoolSetupId);
            return schoolSetup;
        } catch (Exception e) {
            return null;
        }
    }

    public List<SchoolSetup> schoolSetupFindByAttribute(String schoolSetupAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SchoolSetup> listOfSchoolSetup = null;

        String qryString = "";

        qryString = "SELECT e FROM SchoolSetup e ";
        qryString += "WHERE e." + schoolSetupAttribute + " =:schoolSetupAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSchoolSetup = (List<SchoolSetup>) em.createQuery(qryString).setParameter("schoolSetupAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SchoolSetup e ";
                qryString += "WHERE e." + schoolSetupAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSchoolSetup = (List<SchoolSetup>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSchoolSetup = (List<SchoolSetup>) em.createQuery(qryString).setParameter("schoolSetupAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSchoolSetup;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<SchoolSetup>();
    }

    public List<SchoolSetup> schoolSetupGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<SchoolSetup> listOfSchoolSetup = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolSetup e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolSetup e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfSchoolSetup = (List<SchoolSetup>) em.createQuery(qryString).getResultList();

            return listOfSchoolSetup;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<SchoolSetup>();
    }

    public List<SchoolSetup> schoolSetupGetAll(boolean includeLogicallyDeleted) {
        List<SchoolSetup> listOfSchoolSetup = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SchoolSetup e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SchoolSetup e WHERE e.removed = 'NO'";
            }

            listOfSchoolSetup = (List<SchoolSetup>) em.createQuery(qryString).getResultList();

            return listOfSchoolSetup;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<SchoolSetup>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Staff CRUD">

    public String staffCreate(Staff staff) {
        try {

            staff.setRemoved("NO");
            staff.setCreatedDate(new Date());
            em.persist(staff);
            em.flush();
            return staff.getStaffId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean staffDelete(Staff staff, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(staff));
            } else if (permanent == false) {
                staff.setRemoved("YES");
                staff.setEditedDate(new Date());
                em.merge(staff);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean staffUpdate(Staff staff) {
        try {

            staff.setRemoved("NO");
            staff.setEditedDate(new Date());

            em.merge(staff);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public Staff staffFind(String staffId) {
        try {

            Staff staff = em.find(Staff.class, staffId);
            return staff;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Staff> staffFindByAttribute(String staffAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Staff> listOfStaff = null;

        String qryString = "";

        qryString = "SELECT e FROM Staff e ";
        qryString += "WHERE e." + staffAttribute + " =:staffAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStaff = (List<Staff>) em.createQuery(qryString).setParameter("staffAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Staff e ";
                qryString += "WHERE e." + staffAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStaff = (List<Staff>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStaff = (List<Staff>) em.createQuery(qryString).setParameter("staffAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStaff;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Staff>();
    }

    public List<Staff> staffGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<Staff> listOfStaff = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Staff e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Staff e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfStaff = (List<Staff>) em.createQuery(qryString).getResultList();

            return listOfStaff;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Staff>();
    }

    public List<Staff> staffGetAll(boolean includeLogicallyDeleted) {
        List<Staff> listOfStaff = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Staff e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Staff e WHERE e.removed = 'NO'";
            }

            listOfStaff = (List<Staff>) em.createQuery(qryString).getResultList();

            return listOfStaff;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Staff>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StaffQualification CRUD">

    public String staffQualificationCreate(StaffQualification staffQualification) {
        try {

            staffQualification.setRemoved("NO");
            staffQualification.setCreatedDate(new Date());
            em.persist(staffQualification);
            em.flush();
            return staffQualification.getStaffQualificationId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean staffQualificationDelete(StaffQualification staffQualification, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(staffQualification));
            } else if (permanent == false) {
                staffQualification.setRemoved("YES");
                staffQualification.setEditedDate(new Date());
                em.merge(staffQualification);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean staffQualificationUpdate(StaffQualification staffQualification) {
        try {

            staffQualification.setRemoved("NO");
            staffQualification.setEditedDate(new Date());

            em.merge(staffQualification);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public StaffQualification staffQualificationFind(String staffQualificationId) {
        try {

            StaffQualification staffQualification = em.find(StaffQualification.class, staffQualificationId);
            return staffQualification;
        } catch (Exception e) {
            return null;
        }
    }

    public List<StaffQualification> staffQualificationFindByAttribute(String staffQualificationAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StaffQualification> listOfStaffQualification = null;

        String qryString = "";

        qryString = "SELECT e FROM StaffQualification e ";
        qryString += "WHERE e." + staffQualificationAttribute + " =:staffQualificationAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStaffQualification = (List<StaffQualification>) em.createQuery(qryString).setParameter("staffQualificationAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StaffQualification e ";
                qryString += "WHERE e." + staffQualificationAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStaffQualification = (List<StaffQualification>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStaffQualification = (List<StaffQualification>) em.createQuery(qryString).setParameter("staffQualificationAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStaffQualification;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<StaffQualification>();
    }

    public List<StaffQualification> staffQualificationGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<StaffQualification> listOfStaffQualification = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StaffQualification e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StaffQualification e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfStaffQualification = (List<StaffQualification>) em.createQuery(qryString).getResultList();

            return listOfStaffQualification;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<StaffQualification>();
    }

    public List<StaffQualification> staffQualificationGetAll(boolean includeLogicallyDeleted) {
        List<StaffQualification> listOfStaffQualification = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StaffQualification e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StaffQualification e WHERE e.removed = 'NO'";
            }

            listOfStaffQualification = (List<StaffQualification>) em.createQuery(qryString).getResultList();

            return listOfStaffQualification;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<StaffQualification>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Student CRUD">

    public String studentCreate(Student student) {
        try {

            student.setRemoved("NO");
            student.setSelected(false);
            student.setCreatedDate(new Date());
            em.persist(student);
            em.flush();
            return student.getStudentId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentDelete(Student student, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(student));
            } else if (permanent == false) {
                student.setRemoved("YES");
                student.setSelected(false);
                student.setEditedDate(new Date());
                em.merge(student);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean studentUpdate(Student student) {
        try {

            student.setRemoved("NO");
            student.setEditedDate(new Date());
            student.setSelected(false);

            em.merge(student);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public Student studentFind(String studentId) {
        try {

            Student student = em.find(Student.class, studentId);
            return student;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Student> studentFindByAttribute(String studentAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<Student> listOfStudent = null;

        String qryString = "";

        qryString = "SELECT e FROM Student e ";
        qryString += "WHERE e." + studentAttribute + " =:studentAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudent = (List<Student>) em.createQuery(qryString).setParameter("studentAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Student e ORDER BY u.surname,otherNames ";
                qryString += "WHERE e." + studentAttribute + " LIKE '%" + attributeValue + "%' ORDER BY e.surname , e.otherNames";
                listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudent = (List<Student>) em.createQuery(qryString).setParameter("studentAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudent;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Student>();
    }

    public List<Student> studentGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<Student> listOfStudent = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Student e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Student e WHERE e.removed = 'NO' ORDER BY e.surname , e.otherNames";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();

            return listOfStudent;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Student>();
    }

    public List<Student> studentGetAll(boolean includeLogicallyDeleted) {
        List<Student> listOfStudent = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM Student e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM Student e WHERE e.removed = 'NO' ORDER BY e.surname , e.otherNames";
            }

            listOfStudent = (List<Student>) em.createQuery(qryString).getResultList();

            return listOfStudent;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Student>();
    }
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc=" StudentAccount CRUD">

    public String studentAccountCreate(StudentAccount studentAccount) {
        try {

            em.persist(studentAccount);
            em.flush();
            return studentAccount.getStudentAccountId();
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public boolean studentAccountDelete(StudentAccount studentAccount) {
        try {
            em.remove(em.merge(studentAccount));
            em.flush();
            return true;
        } catch (Exception e) {
            appLogger(e);
            return false;
        }
    }

    public boolean studentAccountUpdate(StudentAccount studentAccount) {
        try {

            em.merge(studentAccount);
            em.flush();
            return true;
        } catch (Exception e) {
            appLogger(e);
            return false;
        }
    }

    public StudentAccount studentAccountFindById(String id) {
        try {
            return (StudentAccount) em.find(StudentAccount.class, id);
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public List<StudentAccount> studentAccountGetAll() {
        try {
            String query = "SELECT e FROM StudentAccount e";

            return em.createQuery(query).getResultList();
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public List<StudentAccount> studentAccountFindByAttribute(String parameter, Object paramValue, String paramType) {
        try {
            List<StudentAccount> listOfStudentAccounts = null;
            String query = "SELECT e FROM StudentAccount e WHERE e.paramter LIKE :paramValue;";
            listOfStudentAccounts = em.createQuery(query).setParameter("paramValue", paramValue).getResultList();
            return listOfStudentAccounts;
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }
    //</editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentMark CRUD">

    public String studentMarkCreate(StudentMark studentMark) {
        try {

            studentMark.setRemoved("NO");
            studentMark.setCreatedDate(new Date());
            em.persist(studentMark);
            em.flush();
            return studentMark.getStudentMarkId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentMarkDelete(StudentMark studentMark, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(studentMark));
            } else if (permanent == false) {
                studentMark.setRemoved("YES");
                studentMark.setEditedDate(new Date());
                em.merge(studentMark);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean studentMarkUpdate(StudentMark studentMark) {
        try {

            studentMark.setRemoved("NO");
            studentMark.setEditedDate(new Date());

            em.merge(studentMark);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public StudentMark studentMarkFind(String studentMarkId) {
        try {

            StudentMark studentMark = em.find(StudentMark.class, studentMarkId);
            return studentMark;
        } catch (Exception e) {
            return null;
        }
    }

    public List<StudentMark> studentMarkFindByAttribute(String studentMarkAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentMark> listOfStudentMark = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentMark e ";
        qryString += "WHERE e." + studentMarkAttribute + " =:studentMarkAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).setParameter("studentMarkAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentMark e ";
                qryString += "WHERE e." + studentMarkAttribute + " LIKE '%" + attributeValue + "%'";
                listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).setParameter("studentMarkAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfStudentMark;
        } catch (Exception e) {
            Logger.getLogger(TisCrudSessionbean.class.getName()).log(Level.SEVERE, null, e.getCause());
        }
        return new ArrayList<StudentMark>();
    }

    public List<StudentMark> studentMarkGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<StudentMark> listOfStudentMark = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentMark e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentMark e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).getResultList();

            return listOfStudentMark;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<StudentMark>();
    }

    public List<StudentMark> studentMarkGetAll(boolean includeLogicallyDeleted) {
        List<StudentMark> listOfStudentMark = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentMark e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentMark e WHERE e.removed = 'NO'";
            }

            listOfStudentMark = (List<StudentMark>) em.createQuery(qryString).getResultList();

            return listOfStudentMark;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<StudentMark>();
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" UserAccount CRUD">

    public String userAccountCreate(UserAccount userAccount) {
        try {

            userAccount.setRemoved("NO");
            userAccount.setCreatedDate(new Date());
            em.persist(userAccount);
            em.flush();
            return userAccount.getUserAccountId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean userAccountDelete(UserAccount userAccount, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(userAccount));
            } else if (permanent == false) {
                userAccount.setRemoved("YES");
                userAccount.setEditedDate(new Date());
                em.merge(userAccount);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean userAccountUpdate(UserAccount userAccount) {
        try {

            userAccount.setRemoved("NO");
            userAccount.setEditedDate(new Date());
            em.merge(userAccount);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public UserAccount userAccountFind(String userAccountId) {
        try {

            UserAccount userAccount = em.find(UserAccount.class, userAccountId);
            return userAccount;
        } catch (Exception e) {
            return null;
        }
    }

    public List<UserAccount> userAccountFindByAttribute(String userAccountAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<UserAccount> listOfSemesterRegistrations = null;

        String qryString = "";

        qryString = "SELECT e FROM UserAccount e ";
        qryString += "WHERE e." + userAccountAttribute + " =:userAccountAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSemesterRegistrations = (List<UserAccount>) em.createQuery(qryString).setParameter("userAccountAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM UserAccount e ";
                qryString += "WHERE e." + userAccountAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSemesterRegistrations = (List<UserAccount>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSemesterRegistrations = (List<UserAccount>) em.createQuery(qryString).setParameter("userAccountAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<UserAccount>();
    }

    public List<UserAccount> userAccountGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<UserAccount> listOfSemesterRegistrations = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM UserAccount e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM UserAccount e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfSemesterRegistrations = (List<UserAccount>) em.createQuery(qryString).getResultList();

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<UserAccount>();
    }

    public List<UserAccount> userAccountGetAll(boolean includeLogicallyDeleted) {
        List<UserAccount> listOfUserAccounts = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM UserAccount e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM UserAccount e WHERE e.removed = 'NO'";
            }

            listOfUserAccounts = (List<UserAccount>) em.createQuery(qryString).getResultList();

            return listOfUserAccounts;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<UserAccount>();
    }
    // </editor-fold> 
    //<editor-fold defaultstate="collapsed" desc=" AccessPage CRUD">

    public String accessPageCreate(AccessPage accessPage) {
        try {

            em.persist(accessPage);
            em.flush();
            return accessPage.getAccessPageId();
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public boolean accessPageDelete(AccessPage accessPage) {
        try {
            em.remove(em.merge(accessPage));
            em.flush();
            return true;
        } catch (Exception e) {
            appLogger(e);
            return false;
        }
    }

    public boolean accessPageUpdate(AccessPage accessPage) {
        try {

            em.merge(accessPage);
            em.flush();
            return true;
        } catch (Exception e) {
            appLogger(e);
            return false;
        }
    }

    public AccessPage accessPageFindById(String id) {
        try {
            return (AccessPage) em.find(AccessPage.class, id);
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public List<AccessPage> accessPageGetAll() {
        try {
            String query = "SELECT e FROM AccessPage e";

            return em.createQuery(query).getResultList();
        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }

    public List<AccessPage> accessPageFindByAttribute(String menuName, String userId) {
        List<AccessPage> listOfAccessPage = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM AccessPage e WHERE e.userMenu='" + menuName + "' AND e.userAccount='" + userId + "'"
                    + " ORDER BY e.orderLevel";
            listOfAccessPage = em.createQuery(qryString).getResultList();
            return listOfAccessPage;

        } catch (Exception e) {
            appLogger(e);
            return null;
        }
    }
    //</editor-fold>
      // <editor-fold defaultstate="collapsed" desc=" SemesterRegistration CRUD">
    public String semesterRegistrationCreate(SemesterRegistration SemesterRegistration) {
        try {

           // SemesterRegistration.setSelected(false);
            SemesterRegistration.setRemoved("NO");
           
            em.persist(SemesterRegistration);
            em.flush();
            return SemesterRegistration.getSemesterRegistrationId();

        } catch (Exception e) {
            e.printStackTrace();
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean semesterRegistrationDelete(SemesterRegistration SemesterRegistration, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(SemesterRegistration));
            } else if (permanent == false) {
                SemesterRegistration.setRemoved("YES");
               
                em.merge(SemesterRegistration);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public boolean semesterRegistrationUpdate(SemesterRegistration SemesterRegistration) {
        try {
            //SemesterRegistration.setSelected(false);
            SemesterRegistration.setRemoved("NO");
           
            em.merge(SemesterRegistration);
            em.flush();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;

        }
    }

    public SemesterRegistration semesterRegistrationFind(String semesterRegistrationId) {
        try {

            SemesterRegistration SemesterRegistration = em.find(SemesterRegistration.class, semesterRegistrationId);
            return SemesterRegistration;
        } catch (Exception e) {
            return null;
        }
    }

    public List<SemesterRegistration> semesterRegistrationFindByAttribute(String semesterRegistrationAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<SemesterRegistration> listOfSemesterRegistrations = null;

        String qryString = "";

        qryString = "SELECT e FROM SemesterRegistration e ";
        qryString += "WHERE e." + semesterRegistrationAttribute + " =:semesterRegistrationAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSemesterRegistrations = (List<SemesterRegistration>) em.createQuery(qryString).setParameter("semesterRegistrationAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM SemesterRegistration e ";
                qryString += "WHERE e." + semesterRegistrationAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSemesterRegistrations = (List<SemesterRegistration>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSemesterRegistrations = (List<SemesterRegistration>) em.createQuery(qryString).setParameter("semesterRegistrationAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SemesterRegistration>();
    }

    public List<SemesterRegistration> semesterRegistrationGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<SemesterRegistration> listOfSemesterRegistrations = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SemesterRegistration e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SemesterRegistration e WHERE e.removed = 'NO'";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfSemesterRegistrations = (List<SemesterRegistration>) em.createQuery(qryString).getResultList();

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SemesterRegistration>();
    }

    public List<SemesterRegistration> semesterRegistrationGetAll(boolean includeLogicallyDeleted) {
        List<SemesterRegistration> listOfSemesterRegistrations = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM SemesterRegistration e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM SemesterRegistration e WHERE e.removed = 'NO'";
            }

            listOfSemesterRegistrations = (List<SemesterRegistration>) em.createQuery(qryString).getResultList();

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<SemesterRegistration>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" StudentLevel CRUD">

    public String studentLevelCreate(StudentLevel studentLevel) {
        try {

            // studentLevel.setSelected(false);
            studentLevel.setRemoved("NO");
            em.persist(studentLevel);
            em.flush();
            return studentLevel.getStudentLevelId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return null;

        }
    }

    public boolean studentLevelDelete(StudentLevel studentLevel, boolean permanent) {
        try {

            if (permanent == true) {
                em.remove(em.merge(studentLevel));
            } else if (permanent == false) {
                studentLevel.setRemoved("YES");

                em.merge(studentLevel);
            }
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean studentLevelUpdate(StudentLevel studentLevel) {
        try {
            //studentLevel.setSelected(false);
            studentLevel.setRemoved("NO");

            em.merge(studentLevel);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public StudentLevel studentLevelFind(String studentLevelId) {
        try {

            StudentLevel studentLevel = em.find(StudentLevel.class, studentLevelId);
            return studentLevel;
        } catch (Exception e) {
            return null;
        }
    }

    public List<StudentLevel> studentLevelFindByAttribute(String studentLevelAttribute, Object attributeValue, String fieldType, boolean includeLogicallyDeleted) {
        List<StudentLevel> listOfSemesterRegistrations = null;

        String qryString = "";

        qryString = "SELECT e FROM StudentLevel e ";
        qryString += "WHERE e." + studentLevelAttribute + " =:studentLevelAttribute ";

        try {
            if (includeLogicallyDeleted == true) {
            } else if (includeLogicallyDeleted == false) {
                qryString += " AND e.removed = 'NO'";
            }

            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSemesterRegistrations = (List<StudentLevel>) em.createQuery(qryString).setParameter("studentLevelAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM StudentLevel e ORDER BY e.student.surname , e.student.otherNames ";
                qryString += "WHERE e." + studentLevelAttribute + " LIKE '%" + attributeValue + "%' e.student.surname , e.student.otherNames AND e.removed = 'NO'";
                System.out.println("THE SEARCH BY QUARY IS "+qryString);
                listOfSemesterRegistrations = (List<StudentLevel>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSemesterRegistrations = (List<StudentLevel>) em.createQuery(qryString).setParameter("studentLevelAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<StudentLevel>();
    }

    public List<StudentLevel> studentLevelGetRange(int firstResultIndex, int lastResultIndex, boolean includeLogicallyDeleted) {
        List<StudentLevel> listOfSemesterRegistrations = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentLevel e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentLevel e WHERE e.removed = 'NO' e.student.surname , e.student.otherNames ";
            }

            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfSemesterRegistrations = (List<StudentLevel>) em.createQuery(qryString).getResultList();

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<StudentLevel>();
    }

    public List<StudentLevel> studentLevelGetAll(boolean includeLogicallyDeleted) {
        List<StudentLevel> listOfSemesterRegistrations = null;

        String qryString = "";

        try {
            if (includeLogicallyDeleted == true) {
                qryString = "SELECT e FROM StudentLevel e";
            } else if (includeLogicallyDeleted == false) {
                qryString = "SELECT e FROM StudentLevel e WHERE e.removed = 'NO' e.student.surname , e.student.otherNames ";
            }

            listOfSemesterRegistrations = (List<StudentLevel>) em.createQuery(qryString).getResultList();

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<StudentLevel>();
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc=" Ebo CRUD">
    public String eboCreate(Ebo ebo) {
        try {

            em.persist(ebo);
            em.flush();
            return ebo.getEboId();

        } catch (Exception e) {
            appLogger(e);
            lastActivityExceptionMessage = e.getMessage();
            lastActivityException = e;
            return "";

        }
    }

    public boolean eboDelete(Ebo ebo) {
        try {


            em.remove(em.merge(ebo));
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public boolean eboUpdate(Ebo ebo) {
        try {

            em.merge(ebo);
            em.flush();
            return true;

        } catch (Exception e) {
            appLogger(e);
            return false;

        }
    }

    public Ebo eboFind(String eboId) {
        try {

            Ebo ebo = em.find(Ebo.class, eboId);
            return ebo;
        } catch (Exception e) {
            return null;
        }
    }

    public List<Ebo> eboFindByAttribute(String eboAttribute, Object attributeValue, String fieldType) {
        List<Ebo> listOfSemesterRegistrations = null;

        String qryString = "";

        qryString = "SELECT e FROM Ebo e ";
        qryString += "WHERE e." + eboAttribute + " =:eboAttribute ";

        try {


            if (fieldType.equalsIgnoreCase("NUMBER")) {
                listOfSemesterRegistrations = (List<Ebo>) em.createQuery(qryString).setParameter("eboAttribute", attributeValue).getResultList();
            } else if (fieldType.equalsIgnoreCase("STRING")) {
                qryString = "SELECT e FROM Ebo e ";
                qryString += "WHERE e." + eboAttribute + " LIKE '%" + attributeValue + "%'";
                listOfSemesterRegistrations = (List<Ebo>) em.createQuery(qryString).getResultList();
            } else if (fieldType.equalsIgnoreCase("DATE")) {
                listOfSemesterRegistrations = (List<Ebo>) em.createQuery(qryString).setParameter("eboAttribute", (Date) attributeValue, TemporalType.DATE).getResultList();
            }

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Ebo>();
    }

    public List<Ebo> eboGetRange(int firstResultIndex, int lastResultIndex) {
        List<Ebo> listOfSemesterRegistrations = null;

        String qryString = "";

        try {
            qryString = "SELECT e FROM Ebo e";
            qryString += "LIMIT " + firstResultIndex + "," + lastResultIndex;
            listOfSemesterRegistrations = (List<Ebo>) em.createQuery(qryString).getResultList();

            return listOfSemesterRegistrations;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Ebo>();
    }

    public List<Ebo> eboGetAll() {
        List<Ebo> listOfEbos = null;

        String qryString = "";

        try {

            qryString = "SELECT e FROM Ebo e";
            listOfEbos = (List<Ebo>) em.createQuery(qryString).getResultList();

            return listOfEbos;
        } catch (Exception e) {
            appLogger(e);
        }
        return new ArrayList<Ebo>();
    }
    // </editor-fold>

    void appLogger(Exception e) {
        appLogger(e);
        Logger.getLogger(TisCrudSessionbean.class.getName()).log(Level.SEVERE, e.getMessage(), e);
    }
}
