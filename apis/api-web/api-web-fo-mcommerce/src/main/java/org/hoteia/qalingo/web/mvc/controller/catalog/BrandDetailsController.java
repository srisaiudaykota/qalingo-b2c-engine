/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.catalog;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.RequestConstants;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.service.ProductBrandService;
import org.hoteia.qalingo.core.web.mvc.viewbean.ProductBrandViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;

/**
 * 
 */
@Controller("brandDetailsController")
public class BrandDetailsController extends AbstractMCommerceController {

	@Autowired
	protected ProductBrandService productBrandService;
	
	@RequestMapping("**" + FoUrls.BRAND_DETAILS_URL)
	public ModelAndView brandDetails(final HttpServletRequest request, final Model model, @PathVariable(RequestConstants.URL_PATTERN_BRAND_CODE) final String brandCode) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.BRAND_DETAILS.getVelocityPage());

		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		final ProductBrand productBrand = productBrandService.getProductBrandByCode(currentMarketArea.getId(), brandCode);
		
		final ProductBrandViewBean productBrandViewBean = frontofficeViewBeanFactory.buildProductBrandViewBean(requestUtil.getRequestData(request), productBrand);
		model.addAttribute("productBrand", productBrandViewBean);
		
        return modelAndView;
	}

}