package com.dysm.carchargerdysm;

public class InvoiceData {
    private String userId;
    private String invoicenumber;
    private String rechargeAmount;
    private String energymeterbill;
    private String energymeterwatthour;

    public InvoiceData (String userId,String invoicenumber,String rechargeAmount,String energymeterbill,String energymeterwatthour){
        this.userId=userId;
        this.energymeterbill=energymeterbill;
        this.energymeterwatthour=energymeterwatthour;
        this.rechargeAmount=rechargeAmount;
    }
    public InvoiceData(){

    }
    public void setUserId(String userId) { this.userId = userId; }
    public void setRechargeAmount(String rechargeAmount){this.rechargeAmount=rechargeAmount;}
    public void setEnergymeterbill(String energymeterbill) { this.energymeterbill = energymeterbill; }
    public void setEnergymeterwatthour(String energymeterwatthour) { this.energymeterwatthour = energymeterwatthour; }
}
