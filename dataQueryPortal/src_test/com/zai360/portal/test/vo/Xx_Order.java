package com.zai360.portal.test.vo;

import java.math.BigDecimal;
import java.util.Date;

public class Xx_Order {
    private Long id;

    private Date createDate;

    private Date modifyDate;

    private String address;

    private BigDecimal amountPaid;

    private String areaName;

    private String consignee;

    private BigDecimal couponDiscount;

    private Date expire;

    private BigDecimal fee;

    private BigDecimal freight;

    private String invoiceTitle;

    private Boolean isAllocatedStock;

    private Boolean isInvoice;

    private Date lockExpire;

    private String memo;

    private BigDecimal offsetAmount;

    private Integer orderStatus;

    private String paymentMethodName;

    private Integer paymentStatus;

    private String phone;

    private Long point;

    private String promotion;

    private BigDecimal promotionDiscount;

    private String shippingMethodName;

    private Integer shippingStatus;

    private String sn;

    private BigDecimal tax;

    private String zipCode;

    private Long area;

    private Long couponCode;

    private Long member;

    private Long operator;

    private Long paymentMethod;

    private Long shippingMethod;

    private BigDecimal accountPaid;

    private Date estimatedDeliveryDate;

    private Boolean isReview;

    private Integer sourceType;

    private BigDecimal discountRank;

    private Boolean isHousekeeping;

    private Double discountScale;

    private String cancelReason;

    private Integer refundStatus;

    private String stationName;

    private BigDecimal codPaid;

    private String invoiceType;

    private Integer goodsType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee == null ? null : consignee.trim();
    }

    public BigDecimal getCouponDiscount() {
        return couponDiscount;
    }

    public void setCouponDiscount(BigDecimal couponDiscount) {
        this.couponDiscount = couponDiscount;
    }

    public Date getExpire() {
        return expire;
    }

    public void setExpire(Date expire) {
        this.expire = expire;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    public Boolean getIsAllocatedStock() {
        return isAllocatedStock;
    }

    public void setIsAllocatedStock(Boolean isAllocatedStock) {
        this.isAllocatedStock = isAllocatedStock;
    }

    public Boolean getIsInvoice() {
        return isInvoice;
    }

    public void setIsInvoice(Boolean isInvoice) {
        this.isInvoice = isInvoice;
    }

    public Date getLockExpire() {
        return lockExpire;
    }

    public void setLockExpire(Date lockExpire) {
        this.lockExpire = lockExpire;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public BigDecimal getOffsetAmount() {
        return offsetAmount;
    }

    public void setOffsetAmount(BigDecimal offsetAmount) {
        this.offsetAmount = offsetAmount;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentMethodName() {
        return paymentMethodName;
    }

    public void setPaymentMethodName(String paymentMethodName) {
        this.paymentMethodName = paymentMethodName == null ? null : paymentMethodName.trim();
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion == null ? null : promotion.trim();
    }

    public BigDecimal getPromotionDiscount() {
        return promotionDiscount;
    }

    public void setPromotionDiscount(BigDecimal promotionDiscount) {
        this.promotionDiscount = promotionDiscount;
    }

    public String getShippingMethodName() {
        return shippingMethodName;
    }

    public void setShippingMethodName(String shippingMethodName) {
        this.shippingMethodName = shippingMethodName == null ? null : shippingMethodName.trim();
    }

    public Integer getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode == null ? null : zipCode.trim();
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Long getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(Long couponCode) {
        this.couponCode = couponCode;
    }

    public Long getMember() {
        return member;
    }

    public void setMember(Long member) {
        this.member = member;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Long getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Long paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Long getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(Long shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public BigDecimal getAccountPaid() {
        return accountPaid;
    }

    public void setAccountPaid(BigDecimal accountPaid) {
        this.accountPaid = accountPaid;
    }

    public Date getEstimatedDeliveryDate() {
        return estimatedDeliveryDate;
    }

    public void setEstimatedDeliveryDate(Date estimatedDeliveryDate) {
        this.estimatedDeliveryDate = estimatedDeliveryDate;
    }

    public Boolean getIsReview() {
        return isReview;
    }

    public void setIsReview(Boolean isReview) {
        this.isReview = isReview;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public BigDecimal getDiscountRank() {
        return discountRank;
    }

    public void setDiscountRank(BigDecimal discountRank) {
        this.discountRank = discountRank;
    }

    public Boolean getIsHousekeeping() {
        return isHousekeeping;
    }

    public void setIsHousekeeping(Boolean isHousekeeping) {
        this.isHousekeeping = isHousekeeping;
    }

    public Double getDiscountScale() {
        return discountScale;
    }

    public void setDiscountScale(Double discountScale) {
        this.discountScale = discountScale;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason == null ? null : cancelReason.trim();
    }

    public Integer getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(Integer refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public BigDecimal getCodPaid() {
        return codPaid;
    }

    public void setCodPaid(BigDecimal codPaid) {
        this.codPaid = codPaid;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType == null ? null : invoiceType.trim();
    }

    public Integer getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(Integer goodsType) {
        this.goodsType = goodsType;
    }

	@Override
	public String toString() {
		return "Xx_Order [id=" + id + ", createDate=" + createDate
				+ ", modifyDate=" + modifyDate + ", address=" + address
				+ ", amountPaid=" + amountPaid + ", areaName=" + areaName
				+ ", consignee=" + consignee + ", couponDiscount="
				+ couponDiscount + ", expire=" + expire + ", fee=" + fee
				+ ", freight=" + freight + ", invoiceTitle=" + invoiceTitle
				+ ", isAllocatedStock=" + isAllocatedStock + ", isInvoice="
				+ isInvoice + ", lockExpire=" + lockExpire + ", memo=" + memo
				+ ", offsetAmount=" + offsetAmount + ", orderStatus="
				+ orderStatus + ", paymentMethodName=" + paymentMethodName
				+ ", paymentStatus=" + paymentStatus + ", phone=" + phone
				+ ", point=" + point + ", promotion=" + promotion
				+ ", promotionDiscount=" + promotionDiscount
				+ ", shippingMethodName=" + shippingMethodName
				+ ", shippingStatus=" + shippingStatus + ", sn=" + sn
				+ ", tax=" + tax + ", zipCode=" + zipCode + ", area=" + area
				+ ", couponCode=" + couponCode + ", member=" + member
				+ ", operator=" + operator + ", paymentMethod=" + paymentMethod
				+ ", shippingMethod=" + shippingMethod + ", accountPaid="
				+ accountPaid + ", estimatedDeliveryDate="
				+ estimatedDeliveryDate + ", isReview=" + isReview
				+ ", sourceType=" + sourceType + ", discountRank="
				+ discountRank + ", isHousekeeping=" + isHousekeeping
				+ ", discountScale=" + discountScale + ", cancelReason="
				+ cancelReason + ", refundStatus=" + refundStatus
				+ ", stationName=" + stationName + ", codPaid=" + codPaid
				+ ", invoiceType=" + invoiceType + ", goodsType=" + goodsType
				+ "]";
	}
    
}