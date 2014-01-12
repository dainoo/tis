package com.noad.solutions.common.classes.utils;

import com.noad.solutions.common.locale.utils.GhanaRegion;
import com.noad.solutions.common.locale.utils.PlaceLocality;
import com.noad.solutions.common.locale.utils.Region;
import com.noad.solutions.common.locale.utils.WorldCountry;
import com.noad.solutions.common.string.contants.utils.AcademicStatus;
import com.noad.solutions.common.string.contants.utils.MaritalStatus;
import com.noad.solutions.common.string.contants.utils.Title;
import com.noad.solutions.tis.web.ejbSource.TisEjbSource;
import com.noad.solutions.tis.ejb.entities.AcademicLevel;
import com.noad.solutions.tis.ejb.entities.AcademicYear;
import com.noad.solutions.tis.ejb.entities.Course;
import com.noad.solutions.tis.ejb.entities.Department;
import com.noad.solutions.tis.ejb.entities.Program;
import com.noad.solutions.tis.ejb.entities.Staff;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.model.SelectItem;

/**
 *
 * @author Daud
 */
public class SelectedItemHelper {

    public String selectedStaff = null;
    public String selectedAcademicLevel = null;
    public String selectedCourseCode = null;
    public String selectedAcademicYear = null;
    public String selectedSemester = null;
    public String selectedProgram;
    public String selectType = null;
    public String selectedCourse = null;
    public String selectedStudent = null;
    public Double selectedCourseCreditHours;
    public String selectedDepartment = null;
    public String selectedService = null;
    public String selectedCourseInitials = null;
    public String selectedClient = null;
    public String selectedMedicine = null;
    public String selectedRegion = null;
    public String selectedYear = null;
    public String selectedTitle = null;
    public String selectedCountry = null;
    public String selectedMaritalStatus = null;
    public String selectedAcademicStatus = null;
    public SelectItem[] programSelectItems = null;
    public SelectItem[] countrySelectItems = null;
    public SelectItem[] departmentSelectItems = null;
    public SelectItem[] coursesSelectItems = null;
    public SelectItem[] academicLevelSelectItems = null;
    public SelectItem[] academicYearSelectItems = null;
    public SelectItem[] courseInitialsSelectItems = null;
    public SelectItem[] courseCodeSelectItems = null;
    public SelectItem[] courseCreditHoursSelectItems = null;
    public SelectItem[] teachingStaffSelectItems = null;
    public SelectItem[] titleSelectItems = null;
    public SelectItem[] staffSelectItems = null;
    public SelectItem[] maritalStatusSelectItems = null;
    public SelectItem[] academicStatusSelectItems = null;
    public SelectItem[] distinctAcademicLevelSelectItems = null;
    private SelectItem[] regionSelectItems = null;

    public SelectItem[] getRegionSelectItems() {
        try {
            List<GhanaRegion> listOfRegions = new ArrayList<GhanaRegion>();
            listOfRegions = Region.getRegions();
            regionSelectItems = new SelectItem[listOfRegions.size() + 1];
            regionSelectItems[0]=new SelectItem("", "PLEASE SELECT ONE");
            int counter = 1;
            for (GhanaRegion eachRegion : listOfRegions) {
                regionSelectItems[counter] = new SelectItem(eachRegion.getRegionName(), eachRegion.getRegionName());
                counter++;
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return regionSelectItems;
    }

    public SelectItem[] getDistinctAcademicLevelSelectItems() {
        try {
            List<String> listOfAcademicLevels = new ArrayList<String>();
            listOfAcademicLevels = TisEjbSource.getBasicQuariesInstance().getDistinctAcademicYears();
            System.out.println("DISTINCT ACADEMIC YEAR IN SELECT ITMES IS " + listOfAcademicLevels.size());
            distinctAcademicLevelSelectItems = new SelectItem[listOfAcademicLevels.size() + 1];
            distinctAcademicLevelSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
            int counter = 1;
            for (String eachAcademicLevel : listOfAcademicLevels) {
                distinctAcademicLevelSelectItems[counter] = new SelectItem(eachAcademicLevel, eachAcademicLevel);
                counter++;
            }

        } catch (Exception e) {
            appLogger(e);
        }
        return distinctAcademicLevelSelectItems;
    }

    public SelectItem[] getAcademicStatusSelectItems() {
        List<String> listOfAcademicStatus = new ArrayList<String>();
        listOfAcademicStatus = AcademicStatus.getAcademicStatus();
        academicStatusSelectItems = new SelectItem[listOfAcademicStatus.size() + 1];
        academicStatusSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
        int counter = 1;
        for (String eachStatus : listOfAcademicStatus) {
            academicStatusSelectItems[counter] = new SelectItem(eachStatus, eachStatus);
            counter++;
        }
        return academicStatusSelectItems;
    }

    public SelectItem[] getTitleSelectItems() {
        List<String> listOfTitles = new ArrayList<String>();
        listOfTitles = Title.getTitles();
        titleSelectItems = new SelectItem[listOfTitles.size() + 1];
        titleSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
        int counter = 1;
        for (String eachTitle : listOfTitles) {
            titleSelectItems[counter] = new SelectItem(eachTitle, eachTitle);
            counter++;
        }
        return titleSelectItems;

    }

    public SelectItem[] getMaritalStatusSelectItems() {
        List<String> listOfMaritalStatus = new ArrayList<String>();
        listOfMaritalStatus = MaritalStatus.getMaritalStatus();
        maritalStatusSelectItems = new SelectItem[listOfMaritalStatus.size() + 1];
        maritalStatusSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
        int counter = 1;
        for (String eachStatus : listOfMaritalStatus) {
            maritalStatusSelectItems[counter] = new SelectItem(eachStatus, eachStatus);
            counter++;
        }
        return maritalStatusSelectItems;
    }

    public SelectItem[] getStaffSelectItems() {

        List<Staff> listOfStaffs = new ArrayList<Staff>();
        listOfStaffs = TisEjbSource.getCrudInstance().staffGetAll(false);
        staffSelectItems = new SelectItem[listOfStaffs.size() + 1];
        staffSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE STAFF");
        int counter = 1;
        for (Staff eachTitle : listOfStaffs) {
            staffSelectItems[counter] = new SelectItem(eachTitle.getStaffId(), eachTitle.getSurname());
            counter++;
        }
        return staffSelectItems;
    }

    public SelectItem[] getTeachingStaffSelectItems() {
        try {
            List<Staff> listOfTeachingStaffs = new ArrayList<Staff>();
            listOfTeachingStaffs = TisEjbSource.getBasicQuariesInstance().getTeachingStaff();
            System.out.println("THE LIST OF TEACHING STAFF IS EMPTY " + listOfTeachingStaffs.size());
            if (listOfTeachingStaffs.isEmpty()) {
            } else {
                teachingStaffSelectItems = new SelectItem[listOfTeachingStaffs.size() + 1];
                teachingStaffSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE LECTURER");
                int counter = 1;
                for (Staff eachStaff : listOfTeachingStaffs) {
                    teachingStaffSelectItems[counter] = new SelectItem(eachStaff.getStaffId(), (eachStaff.getSurname() + " " + eachStaff.getOtherNames()));
                    counter++;
                }

            }

        } catch (Exception e) {
        }
        return teachingStaffSelectItems;
    }

    public SelectItem[] getCountrySelectItems() {
        try {
            List<WorldCountry> listOfWorldCountrys = new ArrayList<WorldCountry>();
            listOfWorldCountrys = PlaceLocality.getListOfWorldCountries();
            countrySelectItems = new SelectItem[listOfWorldCountrys.size() + 1];
            countrySelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
            int counter = 1;
            for (WorldCountry eachCountry : listOfWorldCountrys) {
                countrySelectItems[counter] = new SelectItem(eachCountry.getName(), eachCountry.getName());
                counter++;
            }

        } catch (Exception e) {
            appLogger(e);
        }
        return countrySelectItems;
    }

    public SelectItem[] getDepartmentSelectItems() {
        try {
            List<Department> listOfDepartments = new ArrayList<Department>();
            listOfDepartments = TisEjbSource.getCrudInstance().departmentGetAll(false);
            System.out.println("THE SIZE OF DEPT IS " + listOfDepartments.size());
            departmentSelectItems = new SelectItem[listOfDepartments.size()];
            int counter = 0;
            for (Department eachDept : listOfDepartments) {
                departmentSelectItems[counter] = new SelectItem(eachDept.getDepartmentId(), eachDept.getDepartmentName());
                counter++;
            }

        } catch (Exception e) {
            appLogger(e);
        }
        return departmentSelectItems;
    }

    public SelectItem[] getDepartmentNameSelectItems() {
        try {
            List<Department> listOfDepartments = new ArrayList<Department>();
            listOfDepartments = TisEjbSource.getCrudInstance().departmentGetAll(false);
            System.out.println("THE SIZE OF DEPT IS " + listOfDepartments.size());
            setDepartmentSelectItems(new SelectItem[listOfDepartments.size()]);
            int counter = 0;
            for (Department eachDept : listOfDepartments) {
                getDepartmentSelectItems()[counter] = new SelectItem(eachDept.getDepartmentName(), eachDept.getDepartmentName());
                counter++;
            }

        } catch (Exception e) {
            appLogger(e);
        }
        return getDepartmentSelectItems();
    }

    public SelectItem[] getProgramSelectItems() {
        try {
            List<Program> listOfPrograms = new ArrayList<Program>();
            listOfPrograms = TisEjbSource.getCrudInstance().programGetAll(false);
            programSelectItems = new SelectItem[listOfPrograms.size() + 1];
            programSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
            int counter = 1;
            for (Program eachProgram : listOfPrograms) {
                programSelectItems[counter] = new SelectItem(eachProgram.getProgramId(), eachProgram.getProgramName());
                counter++;
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return programSelectItems;
    }

    public SelectItem[] getCoursesSelectItems() {
        try {
            List<Course> listOfCourses = new ArrayList<Course>();
            listOfCourses = TisEjbSource.getCrudInstance().courseGetAll(false);
            coursesSelectItems = new SelectItem[listOfCourses.size() + 1];
            coursesSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
            int counter = 1;
            for (Course eachCourse : listOfCourses) {
                coursesSelectItems[counter] = new SelectItem(eachCourse.getCourseId(), eachCourse.getCourseName());
                counter++;
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return coursesSelectItems;
    }

    public SelectItem[] getAcademicLevelSelectItems() {
        try {
            List<AcademicLevel> listOfAcademicLevels = new ArrayList<AcademicLevel>();
            listOfAcademicLevels = TisEjbSource.getCrudInstance().academicLevelGetAll(false);
            academicLevelSelectItems = new SelectItem[listOfAcademicLevels.size() + 1];
            academicLevelSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
            int counter = 1;
            for (AcademicLevel eachAcademicLevel : listOfAcademicLevels) {
                academicLevelSelectItems[counter] = new SelectItem(eachAcademicLevel.getAcademicLevelId(), eachAcademicLevel.getAcademicLevel());
                counter++;
            }

        } catch (Exception e) {
            appLogger(e);
        }
        return academicLevelSelectItems;
    }

    public SelectItem[] getAcademicYearSelectItems() {
        try {
            List<AcademicYear> listOfAcademicYears = new ArrayList<AcademicYear>();
            listOfAcademicYears = TisEjbSource.getCrudInstance().academicYearGetAll(false);
            academicYearSelectItems = new SelectItem[listOfAcademicYears.size() + 1];
            academicYearSelectItems[0] = new SelectItem("", "PLEASE SELECT ONE");
            int counter = 1;
            for (AcademicYear eachAcademicYear : listOfAcademicYears) {
                academicYearSelectItems[counter] = new SelectItem(eachAcademicYear.getAcademicYearId(), (eachAcademicYear.getAcademicYearId()));
                counter++;
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return academicYearSelectItems;
    }

    public SelectItem[] getCourseInitialsSelectItems() {
        try {
            List<String> listOfCourseInitials = new ArrayList<String>();
            listOfCourseInitials = TisEjbSource.getBasicQuariesInstance().getAllCourseInitials();
            courseInitialsSelectItems = new SelectItem[listOfCourseInitials.size()];
            int initialscounter = 0;
            for (String eachCourseInitials : listOfCourseInitials) {;
                courseInitialsSelectItems[initialscounter] = new SelectItem(eachCourseInitials, eachCourseInitials);
                initialscounter++;
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return courseInitialsSelectItems;
    }

    public SelectItem[] getCourseCodeSelectItems() {
        try {
            List<String> listOfCourseCodes = new ArrayList<String>();
            listOfCourseCodes = TisEjbSource.getBasicQuariesInstance().getAllCourseCodes();
            courseCodeSelectItems = new SelectItem[listOfCourseCodes.size()];
            int codeCounter = 0;
            for (String eachCourseCode : listOfCourseCodes) {
                courseCodeSelectItems[codeCounter] = new SelectItem(eachCourseCode, eachCourseCode);
                codeCounter++;
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return courseCodeSelectItems;
    }

    public SelectItem[] getCourseCreditHoursSelectItems() {
        try {
            List<String> listOfCourseCreditHours = new ArrayList<String>();
            listOfCourseCreditHours = TisEjbSource.getBasicQuariesInstance().getAllCourseCreditHours();
            courseCreditHoursSelectItems = new SelectItem[listOfCourseCreditHours.size()];
            int hoursCounter = 0;
            for (String eachCreditHours : listOfCourseCreditHours) {
                courseCreditHoursSelectItems[hoursCounter] = new SelectItem(eachCreditHours, eachCreditHours);
                hoursCounter++;
            }
        } catch (Exception e) {
            appLogger(e);
        }
        return courseCreditHoursSelectItems;
    }

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    public String getSelectedStaff() {
        return selectedStaff;
    }

    public String getSelectedAcademicLevel() {
        return selectedAcademicLevel;
    }

    public String getSelectedCourseCode() {
        return selectedCourseCode;
    }

    public String getSelectedAcademicYear() {
        return selectedAcademicYear;
    }

    public String getSelectedSemester() {
        return selectedSemester;
    }

    public String getSelectedProgram() {
        return selectedProgram;
    }

    public String getSelectedTitle() {
        return selectedTitle;
    }

    public void setSelectedTitle(String selectedTitle) {
        this.selectedTitle = selectedTitle;
    }

    public void setTitleSelectItems(SelectItem[] titleSelectItems) {
        this.titleSelectItems = titleSelectItems;
    }

    public String getSelectType() {
        return selectType;
    }

    public String getSelectedCourse() {
        return selectedCourse;
    }

    public String getSelectedStudent() {
        return selectedStudent;
    }

    public Double getSelectedCourseCreditHours() {
        return selectedCourseCreditHours;
    }

    public void setSelectedCourseCreditHours(Double selectedCourseCreditHours) {
        this.selectedCourseCreditHours = selectedCourseCreditHours;
    }

    public String getSelectedDepartment() {
        return selectedDepartment;
    }

    public String getSelectedService() {
        return selectedService;
    }

    public String getSelectedCourseInitials() {
        return selectedCourseInitials;
    }

    public String getSelectedClient() {
        return selectedClient;
    }

    public String getSelectedMedicine() {
        return selectedMedicine;
    }

    public String getSelectedRegion() {
        return selectedRegion;
    }

    public String getSelectedYear() {
        return selectedYear;
    }

    public String getSelectedCountry() {
        return selectedCountry;
    }

    public void setSelectedCountry(String selectedCountry) {
        this.selectedCountry = selectedCountry;
    }

    public void setCountrySelectItems(SelectItem[] countrySelectItems) {
        this.countrySelectItems = countrySelectItems;
    }

    public void setSelectedStaff(String selectedStaff) {
        this.selectedStaff = selectedStaff;
    }

    public void setSelectedAcademicLevel(String selectedAcademicLevel) {
        this.selectedAcademicLevel = selectedAcademicLevel;
    }

    public void setSelectedCourseCode(String selectedCourseCode) {
        this.selectedCourseCode = selectedCourseCode;
    }

    public void setSelectedAcademicYear(String selectedAcademicYear) {
        this.selectedAcademicYear = selectedAcademicYear;
    }

    public void setSelectedSemester(String selectedSemester) {
        this.selectedSemester = selectedSemester;
    }

    public void setSelectedProgram(String selectedProgram) {
        this.selectedProgram = selectedProgram;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    public void setSelectedCourse(String selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    public void setSelectedStudent(String selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public void setSelectedDepartment(String selectedDepartment) {
        this.selectedDepartment = selectedDepartment;
    }

    public void setSelectedService(String selectedService) {
        this.selectedService = selectedService;
    }

    public void setSelectedCourseInitials(String selectedCourseInitials) {
        this.selectedCourseInitials = selectedCourseInitials;
    }

    public void setSelectedClient(String selectedClient) {
        this.selectedClient = selectedClient;
    }

    public void setSelectedMedicine(String selectedMedicine) {
        this.selectedMedicine = selectedMedicine;
    }

    public void setSelectedRegion(String selectedRegion) {
        this.selectedRegion = selectedRegion;
    }

    public void setSelectedYear(String selectedYear) {
        this.selectedYear = selectedYear;
    }

    public void setProgramSelectItems(SelectItem[] programSelectItems) {
        this.setProgramSelectItems(programSelectItems);
    }

    public void setDepartmentSelectItems(SelectItem[] departmentSelectItems) {
        this.setDepartmentSelectItems(departmentSelectItems);
    }

    public void setCoursesSelectItems(SelectItem[] coursesSelectItems) {
        this.setCoursesSelectItems(coursesSelectItems);
    }

    public void setAcademicLevelSelectItems(SelectItem[] academicLevelSelectItems) {
        this.setAcademicLevelSelectItems(academicLevelSelectItems);
    }

    public void setAcademicYearSelectItems(SelectItem[] academicYearSelectItems) {
        this.setAcademicYearSelectItems(academicYearSelectItems);
    }
    //</editor-fold>

    static void appLogger(Exception e) {
        e.printStackTrace();
        Logger.getLogger(SelectedItemHelper.class.getName()).log(Level.SEVERE, e.getMessage(), e);
    }

    public void setCourseInitialsSelectItems(SelectItem[] courseInitialsSelectItems) {
        this.courseInitialsSelectItems = courseInitialsSelectItems;
    }

    public void setCourseCodeSelectItems(SelectItem[] courseCodeSelectItems) {
        this.courseCodeSelectItems = courseCodeSelectItems;
    }

    public void setStaffSelectItems(SelectItem[] staffSelectItems) {
        this.staffSelectItems = staffSelectItems;
    }

    public String getSelectedMaritalStatus() {
        return selectedMaritalStatus;
    }

    public void setSelectedMaritalStatus(String selectedMaritalStatus) {
        this.selectedMaritalStatus = selectedMaritalStatus;
    }

    public void setMaritalStatusSelectItems(SelectItem[] maritalStatusSelectItems) {
        this.maritalStatusSelectItems = maritalStatusSelectItems;
    }

    public void setCourseCreditHoursSelectItems(SelectItem[] courseCreditHoursSelectItems) {
        this.courseCreditHoursSelectItems = courseCreditHoursSelectItems;
    }

    public void setTeachingStaffSelectItems(SelectItem[] teachingStaffSelectItems) {
        this.teachingStaffSelectItems = teachingStaffSelectItems;
    }

    public void setAcademicStatusSelectItems(SelectItem[] academicStatusSelectItems) {
        this.academicStatusSelectItems = academicStatusSelectItems;
    }

    public void setDistinctAcademicLevelSelectItems(SelectItem[] distinctAcademicLevelSelectItems) {
        this.distinctAcademicLevelSelectItems = distinctAcademicLevelSelectItems;
    }

    public String getSelectedAcademicStatus() {
        return selectedAcademicStatus;
    }

    public void setSelectedAcademicStatus(String selectedAcademicStatus) {
        this.selectedAcademicStatus = selectedAcademicStatus;
    }

    public void setRegionSelectItems(SelectItem[] regionSelectItems) {
        this.regionSelectItems = regionSelectItems;
    }
}
