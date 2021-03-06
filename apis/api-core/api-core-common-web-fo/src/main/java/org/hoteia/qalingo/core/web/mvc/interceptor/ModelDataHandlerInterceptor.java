package org.hoteia.qalingo.core.web.mvc.interceptor;

import java.util.ArrayList;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hoteia.qalingo.core.ModelConstants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.MarketPlace;
import org.hoteia.qalingo.core.web.mvc.factory.FrontofficeViewBeanFactory;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ModelDataHandlerInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected FrontofficeViewBeanFactory frontofficeViewBeanFactory;

    @Autowired
    protected RequestUtil requestUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, 
                             HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception exception) throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, 
                           Object handler, ModelAndView modelAndView) throws Exception {
        
        try {
            modelAndView.getModelMap().put(ModelConstants.COMMON_VIEW_BEAN, frontofficeViewBeanFactory.buildCommonViewBean(requestUtil.getRequestData(request)));
            
            final MarketPlace currentMarketPlace = requestUtil.getCurrentMarketPlace(request);
            final Market currentMarket = requestUtil.getCurrentMarket(request);
            final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
            final Localization currentLocalization = requestUtil.getCurrentLocalization(request);
            final Customer customer = requestUtil.getCurrentCustomer(request);
            if(customer != null){
                modelAndView.getModelMap().put(ModelConstants.CUSTOMER_VIEW_BEAN, frontofficeViewBeanFactory.buildCustomerViewBean(requestUtil.getRequestData(request), customer));
            }
            
            modelAndView.getModelMap().put(ModelConstants.LEGAl_TERMS_VIEW_BEAN, frontofficeViewBeanFactory.buildLegalTermsViewBean(requestUtil.getRequestData(request)));
            
            modelAndView.getModelMap().put(ModelConstants.CONDITIONS_OF_USE_VIEW_BEAN, frontofficeViewBeanFactory.buildConditionsViewBean(requestUtil.getRequestData(request)));
            
            modelAndView.getModelMap().put(ModelConstants.HEADER_CART, frontofficeViewBeanFactory.buildHeaderCartViewBean(requestUtil.getRequestData(request)));
            
            // ALL MARKETPLACES
            modelAndView.getModelMap().put(ModelConstants.MARKET_PLACES_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketPlaceViewBeans(requestUtil.getRequestData(request)));
            
            // LOCALIZATIONS FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.LANGUAGES_VIEW_BEAN, frontofficeViewBeanFactory.buildLocalizationViewBeansByMarketArea(requestUtil.getRequestData(request), currentLocalization));
            
            // CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREA_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketAreaViewBean(requestUtil.getRequestData(request), currentMarketArea));
            
            // MARKET AREAS FOR THE CURRENT MARKET
            Set<MarketArea> marketAreaList = currentMarket.getMarketAreas();
            modelAndView.getModelMap().put(ModelConstants.MARKET_AREAS_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketAreaViewBeans(requestUtil.getRequestData(request), currentMarket, new ArrayList<MarketArea>(marketAreaList)));
            
            // MARKETS FOR THE CURRENT MARKETPLACE
            Set<Market> marketList = currentMarketPlace.getMarkets();
            modelAndView.getModelMap().put(ModelConstants.MARKETS_VIEW_BEAN, frontofficeViewBeanFactory.buildMarketViewBeans(requestUtil.getRequestData(request), currentMarketPlace, new ArrayList<Market>(marketList)));
            
            // RETAILERS FOR THE CURRENT MARKET AREA
            modelAndView.getModelMap().put(ModelConstants.RETAILERS_VIEW_BEAN, frontofficeViewBeanFactory.buildRetailerViewBeansForTheMarketArea(requestUtil.getRequestData(request)));
            
            // HEADER
            modelAndView.getModelMap().put(ModelConstants.MENUS_VIEW_BEAN, frontofficeViewBeanFactory.buildMenuViewBeans(requestUtil.getRequestData(request)));
            
            // FOOTER
            modelAndView.getModelMap().put(ModelConstants.FOOTER_MENUS_VIEW_BEAN, frontofficeViewBeanFactory.buildFooterMenuViewBeans(requestUtil.getRequestData(request)));
            
        } catch (Exception e) {
            logger.error("inject common datas failed", e);
        }
        
    }

}