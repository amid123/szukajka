/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allegro.domain.dto;

/**
 *
 * @author ringo99
 */
public class SearchQueryModelAdvanced {

    private String searchString1;
    private String searchString2;
    private String searchString3;
    private String searchString4;
    private String searchString5;
    private String searchString6;

    private String searchString7;
    private String searchString8;
    private String searchString9;
    private String searchString10;

    private String searchStringAdvance1;
    private String searchStringAdvance2;
    private String searchStringAdvance3;
    private String searchStringAdvance4;
    private String searchStringAdvance5;
    private String searchStringAdvance6;
    private String searchStringAdvance7;
    private String searchStringAdvance8;
    private String searchStringAdvance9;
    private String searchStringAdvance10;

    private boolean noMatterWithWord;
    private boolean searchInDescripton;
    private boolean lastets24h;
    private boolean onlyBuyNow;
    private boolean searchInFinished;
    private boolean searchInCityByUserLoged;
    private boolean searchInRegionByUserLoged;
    private boolean skipPornResults;
    private boolean pickupInPointOnly;
    private boolean notLogedUsersOnly;
    private boolean freeShiping;
    private boolean signedStandard;
    private boolean auctionOnly;
    private boolean similarOffers;
    private boolean newStateOnly;
    private boolean usedStateOnly;

    public SearchQueryModelAdvanced() {
        this.pickupInPointOnly = false;
        this.usedStateOnly = false;
        this.newStateOnly = false;
        this.similarOffers = false;
        this.auctionOnly = false;
        this.signedStandard = false;
        this.freeShiping = false;
        this.notLogedUsersOnly = false;
        this.skipPornResults = true;
        this.searchInRegionByUserLoged = false;
        this.searchInCityByUserLoged = false;
        this.searchInFinished = false;
        this.onlyBuyNow = false;
        this.lastets24h = false;
        this.searchInDescripton = false;
        this.noMatterWithWord = false;
    }

    public int processQueryOptions() {
        int val = 0;

        if (noMatterWithWord) {
            val=val+1;
        }
        if (searchInDescripton) {
            val=val+2;
        }
        if (lastets24h) {
            val=val+4;
        }
        if (onlyBuyNow) {
            val=val+8;
        }
        if (searchInFinished) {
            val=val+16;
        }
        if (searchInCityByUserLoged) {
            val=val+32;
        }
        if (searchInRegionByUserLoged) {
            val=val+64;
        }
        if (skipPornResults) {
            val=val+128;
        }
        if (pickupInPointOnly) {
            val=val+256;
        }
        if (notLogedUsersOnly) {
            val=val+512;
        }
        if (freeShiping) {
            val=val+1024;
        }
        if (signedStandard) {
            val=val+2048;
        }
        if (auctionOnly) {
            val=val+8192;
        }
        if (similarOffers) {
            val=val+16384;
        }
        if (newStateOnly) {
            val=val+32768;
        }
        if (usedStateOnly) {
            val=val+65536;
        }
        return val;
    }

    public boolean isNoMatterWithWord() {
        return noMatterWithWord;
    }

    public void setNoMatterWithWord(boolean noMatterWithWord) {
        this.noMatterWithWord = noMatterWithWord;
    }

    public boolean isSearchInDescripton() {
        return searchInDescripton;
    }

    public void setSearchInDescripton(boolean searchInDescripton) {
        this.searchInDescripton = searchInDescripton;
    }

    public boolean isLastets24h() {
        return lastets24h;
    }

    public void setLastets24h(boolean lastets24h) {
        this.lastets24h = lastets24h;
    }

    public boolean isOnlyBuyNow() {
        return onlyBuyNow;
    }

    public void setOnlyBuyNow(boolean onlyBuyNow) {
        this.onlyBuyNow = onlyBuyNow;
    }

    public boolean isSearchInFinished() {
        return searchInFinished;
    }

    public void setSearchInFinished(boolean searchInFinished) {
        this.searchInFinished = searchInFinished;
    }

    public boolean isSearchInCityByUserLoged() {
        return searchInCityByUserLoged;
    }

    public void setSearchInCityByUserLoged(boolean searchInCityByUserLoged) {
        this.searchInCityByUserLoged = searchInCityByUserLoged;
    }

    public boolean isSearchInRegionByUserLoged() {
        return searchInRegionByUserLoged;
    }

    public void setSearchInRegionByUserLoged(boolean searchInRegionByUserLoged) {
        this.searchInRegionByUserLoged = searchInRegionByUserLoged;
    }

    public boolean isSkipPornResults() {
        return skipPornResults;
    }

    public void setSkipPornResults(boolean skipPornResults) {
        this.skipPornResults = skipPornResults;
    }

    public boolean isPickupInPointOnly() {
        return pickupInPointOnly;
    }

    public void setPickupInPointOnly(boolean pickupInPointOnly) {
        this.pickupInPointOnly = pickupInPointOnly;
    }

    public boolean isNotLogedUsersOnly() {
        return notLogedUsersOnly;
    }

    public void setNotLogedUsersOnly(boolean notLogedUsersOnly) {
        this.notLogedUsersOnly = notLogedUsersOnly;
    }

    public boolean isFreeShiping() {
        return freeShiping;
    }

    public void setFreeShiping(boolean freeShiping) {
        this.freeShiping = freeShiping;
    }

    public boolean isSignedStandard() {
        return signedStandard;
    }

    public void setSignedStandard(boolean signedStandard) {
        this.signedStandard = signedStandard;
    }

    public boolean isAuctionOnly() {
        return auctionOnly;
    }

    public void setAuctionOnly(boolean auctionOnly) {
        this.auctionOnly = auctionOnly;
    }

    public boolean isSimilarOffers() {
        return similarOffers;
    }

    public void setSimilarOffers(boolean similarOffers) {
        this.similarOffers = similarOffers;
    }

    public boolean isNewStateOnly() {
        return newStateOnly;
    }

    public void setNewStateOnly(boolean newStateOnly) {
        this.newStateOnly = newStateOnly;
    }

    public boolean isUsedStateOnly() {
        return usedStateOnly;
    }

    public void setUsedStateOnly(boolean usedStateOnly) {
        this.usedStateOnly = usedStateOnly;
    }

    public String getSearchString1() {
        return searchString1;
    }

    public void setSearchString1(String searchString1) {
        this.searchString1 = searchString1;
    }

    public String getSearchString2() {
        return searchString2;
    }

    public void setSearchString2(String searchString2) {
        this.searchString2 = searchString2;
    }

    public String getSearchString3() {
        return searchString3;
    }

    public void setSearchString3(String searchString3) {
        this.searchString3 = searchString3;
    }

    public String getSearchString4() {
        return searchString4;
    }

    public void setSearchString4(String searchString4) {
        this.searchString4 = searchString4;
    }

    public String getSearchString5() {
        return searchString5;
    }

    public void setSearchString5(String searchString5) {
        this.searchString5 = searchString5;
    }

    public String getSearchString6() {
        return searchString6;
    }

    public void setSearchString6(String searchString6) {
        this.searchString6 = searchString6;
    }

    public String getSearchString7() {
        return searchString7;
    }

    public void setSearchString7(String searchString7) {
        this.searchString7 = searchString7;
    }

    public String getSearchString8() {
        return searchString8;
    }

    public void setSearchString8(String searchString8) {
        this.searchString8 = searchString8;
    }

    public String getSearchString9() {
        return searchString9;
    }

    public void setSearchString9(String searchString9) {
        this.searchString9 = searchString9;
    }

    public String getSearchString10() {
        return searchString10;
    }

    public void setSearchString10(String searchString10) {
        this.searchString10 = searchString10;
    }

    public String getSearchStringAdvance1() {
        return searchStringAdvance1;
    }

    public void setSearchStringAdvance1(String searchStringAdvance1) {
        this.searchStringAdvance1 = searchStringAdvance1;
    }

    public String getSearchStringAdvance2() {
        return searchStringAdvance2;
    }

    public void setSearchStringAdvance2(String searchStringAdvance2) {
        this.searchStringAdvance2 = searchStringAdvance2;
    }

    public String getSearchStringAdvance3() {
        return searchStringAdvance3;
    }

    public void setSearchStringAdvance3(String searchStringAdvance3) {
        this.searchStringAdvance3 = searchStringAdvance3;
    }

    public String getSearchStringAdvance4() {
        return searchStringAdvance4;
    }

    public void setSearchStringAdvance4(String searchStringAdvance4) {
        this.searchStringAdvance4 = searchStringAdvance4;
    }

    public String getSearchStringAdvance5() {
        return searchStringAdvance5;
    }

    public void setSearchStringAdvance5(String searchStringAdvance5) {
        this.searchStringAdvance5 = searchStringAdvance5;
    }

    public String getSearchStringAdvance6() {
        return searchStringAdvance6;
    }

    public void setSearchStringAdvance6(String searchStringAdvance6) {
        this.searchStringAdvance6 = searchStringAdvance6;
    }

    public String getSearchStringAdvance7() {
        return searchStringAdvance7;
    }

    public void setSearchStringAdvance7(String searchStringAdvance7) {
        this.searchStringAdvance7 = searchStringAdvance7;
    }

    public String getSearchStringAdvance8() {
        return searchStringAdvance8;
    }

    public void setSearchStringAdvance8(String searchStringAdvance8) {
        this.searchStringAdvance8 = searchStringAdvance8;
    }

    public String getSearchStringAdvance9() {
        return searchStringAdvance9;
    }

    public void setSearchStringAdvance9(String searchStringAdvance9) {
        this.searchStringAdvance9 = searchStringAdvance9;
    }

    public String getSearchStringAdvance10() {
        return searchStringAdvance10;
    }

    public void setSearchStringAdvance10(String searchStringAdvance10) {
        this.searchStringAdvance10 = searchStringAdvance10;
    }

    @Override
    public String toString() {
        return "SearchQueryModel{" + "searchString=" + ", searchString1=" + searchString1 + ", searchString2=" + searchString2 + ", searchString3=" + searchString3 + ", searchString4=" + searchString4 + ", searchString5=" + searchString5 + ", searchString6=" + searchString6 + ", searchString7=" + searchString7 + ", searchString8=" + searchString8 + ", searchString9=" + searchString9 + ", searchString10=" + searchString10 + '}';
    }

}
