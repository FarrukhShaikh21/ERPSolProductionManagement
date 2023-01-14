package erpsolpms.erpsolpmsview.erpsolpmsclass;

import erpsolglob.erpsolglobmodel.erpsolglobclasses.ERPSolGlobClassModel;

import erpsolglob.erpsolglobview.erpclass.ERPSolGlobalViewBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import oracle.adf.model.AttributeBinding;
import oracle.adf.model.OperationBinding;
import oracle.adf.model.binding.DCIteratorBinding;
import oracle.adf.share.ADFContext;
import oracle.adf.view.rich.component.rich.RichPopup;
import oracle.adf.view.rich.event.DialogEvent;

import oracle.binding.BindingContainer;

import oracle.jbo.ApplicationModule;
import oracle.jbo.JboException;
import oracle.jbo.ViewObject;

import org.apache.myfaces.trinidad.render.ExtendedRenderKitService;
import org.apache.myfaces.trinidad.util.Service;

public class ERPSolPMSBean {
    public ERPSolPMSBean() {
        super();
    }

    String ERPSolStrUserCode;
    String ERPSolStrUserLocationCode;
    String ERPSolStrUserRegionCode;
    String ERPSolStrUserStoreCode;
    RichPopup ERPSolImeiPopup;
//    RichInputText ERPSolImeiBoxText;
    String ERPSolReportName;
    public void doSetERPSolAPSSessionGlobals() {
        System.out.println("glob user code"+getERPSolStrUserCode());
        if (getERPSolStrUserCode().length()==0) {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users Defaults are not defined properly. Please Check"));
           throw new JboException("Users Defaults are not defined properly. Please Check");
       }
        try {
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_USER_CODE", getERPSolStrUserCode().toString());
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_USER_REGION", getERPSolStrUserCode().toString());
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_USER_LOCATION",getERPSolStrUserLocationCode().toString());
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_USER_STORE", getERPSolStrUserStoreCode().toString());
            ADFContext.getCurrent().getPageFlowScope().put("GLOB_COMPANY_CODE", "D");
        } catch (Exception e) {
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Users Defaults are not defined properly. Please Check"));
        }
    }


    public void setERPSolStrUserCode(String ERPSolStrUserCode) {
        this.ERPSolStrUserCode = ERPSolStrUserCode;
    }

    public String getERPSolStrUserCode() {
        return ERPSolStrUserCode;
    }

    public void setERPSolStrUserLocationCode(String ERPSolStrUserLocationCode) {
        this.ERPSolStrUserLocationCode = ERPSolStrUserLocationCode;
    }

    public String getERPSolStrUserLocationCode() {
        return ERPSolStrUserLocationCode;
    }

    public void setERPSolStrUserRegionCode(String ERPSolStrUserRegionCode) {
        this.ERPSolStrUserRegionCode = ERPSolStrUserRegionCode;
    }

    public String getERPSolStrUserRegionCode() {
        return ERPSolStrUserRegionCode;
    }

    public void setERPSolStrUserStoreCode(String ERPSolStrUserStoreCode) {
        this.ERPSolStrUserStoreCode = ERPSolStrUserStoreCode;
    }

    public String getERPSolStrUserStoreCode() {
        return ERPSolStrUserStoreCode;
    }

    public List<SelectItem> doERPSolGetAutoSuggestedStoreValues(String pStringValues) {
//public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding ERPLocid =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        System.out.println("c");
//        String ERPLocid=""+ERPSolib.getCurrentRow().getAttribute("Locationid");
        System.out.println("d");
        System.out.println(ERPLocid.getInputValue());//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllStoresAutoSuggestRO",
                                                            "LOCATIONID='"+ERPLocid.getInputValue()+"' AND UPPER(CONCAT(STOREID,STORE_NAME))", "StoreName", "Storeid", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }
  
    public List<SelectItem> doERPSolGetAutoSuggestedBranchValues(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        AttributeBinding ERPPaymentMode =(AttributeBinding)ERPSolbc.getControlBinding("PaymentMode");
        
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllBankBranchesAutoSuggestRO",
                                                                        "CASH_BANK='"+(ERPPaymentMode.getInputValue().equals("CSH")?"C":"B")+"' AND UPPER(CONCAT(Branch_Name,Branchid))", "BranchName", "Branchid", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }

  
    public List<SelectItem> doERPSolGetAutoSuggestedReceiveStoreValues(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllStoresReceivingAutoSuggestRO",
                                                            " UPPER(CONCAT(STOREID,STORE_NAME))", "StoreName", "Storeid", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }
    public List<SelectItem> doERPSolGetAutoSuggestedGRNSourceDocValues(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ERPSolIB=(DCIteratorBinding)ERPSolbc.get("InReceivedItemsCRUDIterator");
        ApplicationModule ERPSolAM=ERPSolIB.getViewObject().getApplicationModule();
        ViewObject vo=ERPSolAM.findViewObject("VWGRNSourceDocNoAutoSuggestRO");
        AttributeBinding ERPStoreid         =(AttributeBinding)ERPSolbc.getControlBinding("Storeid");
        AttributeBinding ERPReceivingDate   =(AttributeBinding)ERPSolbc.getControlBinding("ReceivingDate");
        vo.setNamedWhereClauseParam("P_ADF_DATE", ERPReceivingDate.getInputValue());
        vo.setNamedWhereClauseParam("P_ADF_DOC_TYPE", "STA");
        vo.setNamedWhereClauseParam("P_ADF_STOREID", ERPStoreid.getInputValue());
        vo.executeQuery();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "VWGRNSourceDocNoAutoSuggestRO",
                                                            " UPPER(CONCAT(Stnno,Sname))", "Stnno", "Sname", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }

    public List<SelectItem> doERPSolGetAutoSuggestedPaymentDocument(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ERPSolIB=(DCIteratorBinding)ERPSolbc.get("PaymentMasterCRUDIterator");
        ApplicationModule ERPSolAM=ERPSolIB.getViewObject().getApplicationModule();
        ViewObject vo=ERPSolAM.findViewObject("VWBilllBalanceAmountForPayAutoSuggestRO");
        AttributeBinding ERPSupplierid         =(AttributeBinding)ERPSolbc.getControlBinding("Supplierid");
        vo.setNamedWhereClauseParam("P_ADF_BILL_TYPE", "OBILL");
        vo.setNamedWhereClauseParam("P_ADF_SUPPLIERID", ERPSupplierid.getInputValue());
        vo.executeQuery();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "VWBilllBalanceAmountForPayAutoSuggestRO",
                                                            " UPPER(CONCAT(Billid,Supplier_Name))", "Billid", "SupplierName", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }
    
   
    public List<SelectItem> doERPSolGetAutoSuggestedCOAValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "VWChartOfAccountAutoSuggestRO"," UPPER(CONCAT(GLCODE,Description))", "Glcode", "Description", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }  
    
    public List<SelectItem> doERPSolGetAutoSuggestedCostCenterValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "VWCostCenterAutoSuggestRO"," UPPER(CONCAT(Costcenterid,Description))", "Costcenterid", "Description", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }  
    
    public List<SelectItem> doERPSolGetAutoSuggestedCustomerValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding ERPLocid =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        System.out.println("c");
        
        System.out.println("d");
        System.out.println(ERPLocid);//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllCustomersAutoSuggestRO",
                                                            "LOCATIONID='"+ERPLocid.getInputValue()+"' AND UPPER(CONCAT(CUSTOMERID,CUSTOMER_NAME))", "CustomerName", "Customerid", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }   

    public List<SelectItem> doERPSolGetAutoSuggestedSalesPersonValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding ERPCustomerId =(AttributeBinding)ERPSolbc.getControlBinding("Customerid");
        System.out.println("c");
//        String ERPCustomerId=""+ERPSolib.getCurrentRow().getAttribute("Customerid");
        System.out.println("d");
        System.out.println(ERPCustomerId);//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "SoSalesPersonsAutoSuggestRO",
                                                            "SALESPERSONID IN (SELECT ASP.SALESPERSONID from ALL_CUSTOMER_SALESPERSON ASP WHERE ASP.CUSTOMERID='"+ERPCustomerId.getInputValue()+"') AND UPPER(CONCAT(Salespersonid,name))", "Name", "Salespersonid", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }   
   
    public List<SelectItem> doERPSolGetAutoSuggestedSupplierValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
//        Attribute ERPSolib =(Attribute)ERPSolbc.get("SoSalesOrderViewCRUDIterator");
        System.out.println("c");
        AttributeBinding ERPLocid =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        System.out.println("d");
        System.out.println(ERPLocid.getInputValue());//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "PuSuppliersAutoSuggestRO",
                                                            "LOCATIONID='"+ERPLocid.getInputValue()+"' AND UPPER(CONCAT(SUPPLIERID,SUPP_NAME))", "SuppName", "Supplierid", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }   
     
    
    public List<SelectItem> doERPSolGetAutoSuggestedModelValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "InItemsAutoSuggestRO"," UPPER(CONCAT(Productid,Model_No))", "Productid", "ModelNo", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }   
    public void doERPSolPaymentsDialogConfirm(DialogEvent erpsolde) {
        if (erpsolde.getOutcome()==DialogEvent.Outcome.yes) {
            OperationBinding binding = ERPSolGlobalViewBean.doIsERPSolGerOperationBinding("doSupervisePayments");
            binding.execute();
        }
    }

    public void doERPSolNoteDialogConfirm(DialogEvent erpsolde) {
        if (erpsolde.getOutcome()==DialogEvent.Outcome.yes) {
            OperationBinding binding = ERPSolGlobalViewBean.doIsERPSolGerOperationBinding("doSuperviseNote");
            binding.execute();
        }
    }
    

    public void doERPSolOtherBillsDialogConfirm(DialogEvent erpsolde) {
        if (erpsolde.getOutcome()==DialogEvent.Outcome.yes) {
            OperationBinding binding = ERPSolGlobalViewBean.doIsERPSolGerOperationBinding("doSuperviseOtherBills");
            binding.execute();
        }
    }

    public void setERPSolImeiPopup(RichPopup ERPSolImeiPopup) {
        this.ERPSolImeiPopup = ERPSolImeiPopup;
    }

    public RichPopup getERPSolImeiPopup() {
        return ERPSolImeiPopup;
    }

    public String DoShowERPSolImeiPopup() {
        RichPopup.PopupHints ERPSolHints=new RichPopup.PopupHints();
        getERPSolImeiPopup().show(ERPSolHints);
        return null;
    }
   

   

    public String getERPSolImeiString() {
        return null;
    }

    public String doERPSolExecutAPSReport() {
        BindingContainer bc = ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ib=(DCIteratorBinding)bc.get("SysProgramDetROIterator");
        ApplicationModule am=ib.getViewObject().getApplicationModule();
        ViewObject vo=am.findViewObject("QVOAPSReport");
        if (vo!=null) {
            vo.remove();
       }
        
        vo=am.createViewObjectFromQueryStmt("QVOAPSReport", "select PARAMETER_VALUE FROM so_sales_parameter a where a.Parameter_Id='REPORT_SERVER_URL'");
        vo.executeQuery();
        String pReportUrl=vo.first().getAttribute(0).toString();
        vo.remove();
        vo=am.createViewObjectFromQueryStmt("QVOAPSReport", "select PATH PATH FROM SYSTEM a where a.PROJECTID='PY' ");
        vo.executeQuery();
        String pReportPath=vo.first().getAttribute(0).toString()+"REPORTS\\\\";
        System.out.println(pReportPath);
        pReportPath=pReportPath+ERPSolReportName;
        
    
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding ERPCompanyid       =(AttributeBinding)ERPSolbc.getControlBinding("txtCompanyId");
        AttributeBinding ERPLocationid      =(AttributeBinding)ERPSolbc.getControlBinding("Locationid");
        AttributeBinding ERPSupplierId       =(AttributeBinding)ERPSolbc.getControlBinding("txtSupplierId");
        AttributeBinding ERPFromDate        =(AttributeBinding)ERPSolbc.getControlBinding("txtFromDate");
        AttributeBinding ERPToDate          =(AttributeBinding)ERPSolbc.getControlBinding("txtToDate");
        String reportParameter="";
        reportParameter="P_COMPANY_ID="+ (ERPCompanyid.getInputValue()==null?"":ERPCompanyid.getInputValue());
        reportParameter+="&P_LOCID="+(ERPLocationid.getInputValue()==null?"":ERPLocationid.getInputValue());
        reportParameter+="&P_SUPPLIER_ID="+(ERPSupplierId.getInputValue()==null?"":ERPSupplierId.getInputValue());
        reportParameter+="&P_FROM_DATE="+(ERPFromDate.getInputValue()==null?"":doERPSolGetFormatDate(""+ERPFromDate.getInputValue() ) );
        reportParameter+="&P_TO_DATE="+(ERPToDate.getInputValue()==null?"":doERPSolGetFormatDate(""+ERPToDate.getInputValue())  );
        reportParameter+="&USER="+ERPSolGlobClassModel.doGetUserCode();
        
        pReportUrl=pReportUrl.replace("<P_REPORT_PATH>", pReportPath);
        pReportUrl=pReportUrl.replace("<P_REPORT_PARAMETERS>", reportParameter);
        
        System.out.println(pReportPath);
        System.out.println(reportParameter);
        System.out.println(pReportUrl);
        
        doErpSolOpenReportTab(pReportUrl);
        return null;
    }
    public void doErpSolOpenReportTab(String url) {
    ExtendedRenderKitService erks =
    Service.getRenderKitService(FacesContext.getCurrentInstance(), ExtendedRenderKitService.class);
    StringBuilder strb = new StringBuilder("window.open('" + url + "');");
    erks.addScript(FacesContext.getCurrentInstance(), strb.toString());
    }


    public void setERPSolReportName(String ERPSolReportName) {
        this.ERPSolReportName = ERPSolReportName;
    }

    public String getERPSolReportName() {
        return ERPSolReportName;
    }
    public String doERPSolGetFormatDate(String pDate) {
     
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String fromDate="";
        try {
            Date dt = sdf.parse(pDate);
            sdf=new SimpleDateFormat("dd-MMM-yyyy");
            fromDate=sdf.format(dt).toUpperCase();
            return fromDate;
        }
        catch (NullPointerException npe) {
            System.out.println("null parseexception");
        }
        catch (ParseException e) {
            System.out.println("parseexception");
        }
        /////////////
           
        return null;
    }


    public List<SelectItem> doERPSolGetAutoSuggestedLocationValues(String pStringValues) {
    //public static List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        //public List<SelectItem> doERPSolGetAutoSuggestedValues(String pSearch,String pViewObjectName,String pWhereColumn,String pAttribute1,String pAttribute2,Integer pNoOfRecordsSuggest) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "AllLocationsAutoSuggestRO",
                                                            "UPPER(CONCAT(Locationid,Location_Description))", "LocationDescription", "Locationid", 10,"ERPSolAPSAppModuleDataControl");
        return ResultList;
        
    }


    public String doERPSolOtherBillReport() {
        BindingContainer bc = ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ib=(DCIteratorBinding)bc.get("OtherBillsCRUDIterator");
        ApplicationModule am=ib.getViewObject().getApplicationModule();
        ViewObject vo=am.findViewObject("QVOOtherBill");
        if (vo!=null) {
            vo.remove();
       }
        
        vo=am.createViewObjectFromQueryStmt("QVOOtherBill", "select PARAMETER_VALUE FROM so_sales_parameter a where a.Parameter_Id='REPORT_SERVER_URL'");
        vo.executeQuery();
        String pReportUrl=vo.first().getAttribute(0).toString();
        vo.remove();
        vo=am.createViewObjectFromQueryStmt("QVOOtherBill", "select PATH PATH FROM SYSTEM a where a.PROJECTID='PY' ");
        vo.executeQuery();
        String pReportPath=vo.first().getAttribute(0).toString()+"REPORTS\\\\";
        System.out.println(pReportPath);
        pReportPath=pReportPath+"RPT_OTHER_BILLT.RDF";
        
    
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding Billidseq          =(AttributeBinding)ERPSolbc.getControlBinding("Billidseq");
        String reportParameter="";
        reportParameter="P_BILL_SEQ="+ (Billidseq.getInputValue()==null?"":Billidseq.getInputValue());
        reportParameter+="&USER="+ERPSolGlobClassModel.doGetUserCode();
        pReportUrl=pReportUrl.replace("<P_REPORT_PATH>", pReportPath);
        pReportUrl=pReportUrl.replace("<P_REPORT_PARAMETERS>", reportParameter);
        
        System.out.println(pReportPath);
        System.out.println(reportParameter);
        System.out.println(pReportUrl);
        
        doErpSolOpenReportTab(pReportUrl);
        return null;
    }
    
    public String doERPSolPaymentReport() {
        BindingContainer bc = ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ib=(DCIteratorBinding)bc.get("PaymentMasterCRUDIterator");
        ApplicationModule am=ib.getViewObject().getApplicationModule();
        ViewObject vo=am.findViewObject("QVOPayment");
        if (vo!=null) {
            vo.remove();
       }
        
        vo=am.createViewObjectFromQueryStmt("QVOPayment", "select PARAMETER_VALUE FROM so_sales_parameter a where a.Parameter_Id='REPORT_SERVER_URL'");
        vo.executeQuery();
        String pReportUrl=vo.first().getAttribute(0).toString();
        vo.remove();
        vo=am.createViewObjectFromQueryStmt("QVOPayment", "select PATH PATH FROM SYSTEM a where a.PROJECTID='PY' ");
        vo.executeQuery();
        String pReportPath=vo.first().getAttribute(0).toString()+"REPORTS\\\\";
        System.out.println(pReportPath);
        pReportPath=pReportPath+"RPT_PAYMENT.RDF";
        
    
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding PaymentSeq          =(AttributeBinding)ERPSolbc.getControlBinding("Paymentseq");
        String reportParameter="";
        reportParameter="P_PAYMENT_SEQ="+ (PaymentSeq.getInputValue()==null?"":PaymentSeq.getInputValue());
        reportParameter+="&USER="+ERPSolGlobClassModel.doGetUserCode();
        pReportUrl=pReportUrl.replace("<P_REPORT_PATH>", pReportPath);
        pReportUrl=pReportUrl.replace("<P_REPORT_PARAMETERS>", reportParameter);
        
        System.out.println(pReportPath);
        System.out.println(reportParameter);
        System.out.println(pReportUrl);
        
        doErpSolOpenReportTab(pReportUrl);
        return null;
    }
  
    public String doERPSolNoteReport() {
        BindingContainer bc = ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ib=(DCIteratorBinding)bc.get("NoteMasterCRUDIterator");
        ApplicationModule am=ib.getViewObject().getApplicationModule();
        ViewObject vo=am.findViewObject("QVOPayment");
        if (vo!=null) {
            vo.remove();
       }
        
        vo=am.createViewObjectFromQueryStmt("QVOPayment", "select PARAMETER_VALUE FROM so_sales_parameter a where a.Parameter_Id='REPORT_SERVER_URL'");
        vo.executeQuery();
        String pReportUrl=vo.first().getAttribute(0).toString();
        vo.remove();
        vo=am.createViewObjectFromQueryStmt("QVOPayment", "select PATH PATH FROM SYSTEM a where a.PROJECTID='PY' ");
        vo.executeQuery();
        String pReportPath=vo.first().getAttribute(0).toString()+"REPORTS\\\\";
        System.out.println(pReportPath);
        pReportPath=pReportPath+"RPT_PAY_NOTE.RDF";
        
    
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        System.out.println("b");
        AttributeBinding NoteSeq          =(AttributeBinding)ERPSolbc.getControlBinding("Notecodeseq");
        String reportParameter="";
        reportParameter="P_NOTE_SEQ="+ (NoteSeq.getInputValue()==null?"":NoteSeq.getInputValue());
        reportParameter+="&USER="+ERPSolGlobClassModel.doGetUserCode();
        pReportUrl=pReportUrl.replace("<P_REPORT_PATH>", pReportPath);
        pReportUrl=pReportUrl.replace("<P_REPORT_PARAMETERS>", reportParameter);
        
        System.out.println(pReportPath);
        System.out.println(reportParameter);
        System.out.println(pReportUrl);
        
        doErpSolOpenReportTab(pReportUrl);
        return null;
    }
    
    public List<SelectItem> doERPSolGetAutoSuggestedAPUnsubmitDoc(String pStringValues) {
        List<SelectItem> ResultList=new ArrayList<SelectItem>();
        System.out.println("a");
        BindingContainer ERPSolbc=ERPSolGlobalViewBean.doGetERPBindings();
        DCIteratorBinding ERPSolIB=(DCIteratorBinding)ERPSolbc.get("VWPayPayableReportROIterator");
        ApplicationModule ERPSolAM=ERPSolIB.getViewObject().getApplicationModule();
        System.out.println("b");
        String ERPLocid=ERPSolGlobClassModel.doGetUserLocationCode();
        AttributeBinding ERPDocType =(AttributeBinding)ERPSolbc.getControlBinding("txtDocTypeId");
        ViewObject vo=ERPSolAM.findViewObject("VWPayDocumentIdForUnsubmitAutoSuggestRO");
        vo.setNamedWhereClauseParam("P_ADF_DOCTYPEID", ERPDocType.getInputValue());
        vo.setNamedWhereClauseParam("P_ADF_LOCATIONID", ERPLocid);
        vo.executeQuery();
        System.out.println("d");
        System.out.println(ERPLocid);//ERPSolGlobalViewBean.
        ResultList= ERPSolGlobalViewBean.doERPSolGetAutoSuggestedValues(pStringValues, "VWPayDocumentIdForUnsubmitAutoSuggestRO",
                                                            " UPPER(CONCAT(DOCUMENT_ID,CUSTOMER_NAME))", "DocumentId", "Description", 10);
        return ResultList;
        
    }  

}


