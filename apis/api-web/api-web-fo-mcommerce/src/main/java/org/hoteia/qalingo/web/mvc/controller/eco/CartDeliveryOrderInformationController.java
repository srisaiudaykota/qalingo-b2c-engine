/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.controller.eco;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAddress;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.web.mvc.viewbean.CartViewBean;
import org.hoteia.qalingo.core.web.mvc.viewbean.CustomerAddressViewBean;
import org.hoteia.qalingo.core.web.servlet.ModelAndViewThemeDevice;
import org.hoteia.qalingo.core.web.servlet.view.RedirectView;
import org.hoteia.qalingo.web.mvc.controller.AbstractMCommerceController;
import org.hoteia.qalingo.web.mvc.form.CartForm;

/**
 * 
 */
@Controller("cartDeliveryOrderInformationController")
public class CartDeliveryOrderInformationController extends AbstractMCommerceController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@RequestMapping(value = FoUrls.CART_DELIVERY_URL, method = RequestMethod.GET)
	public ModelAndView displayOrderDelivery(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		ModelAndViewThemeDevice modelAndView = new ModelAndViewThemeDevice(getCurrentVelocityPath(request), FoUrls.CART_DELIVERY.getVelocityPage());

		// SANITY CHECK
		final Cart currentCart = requestUtil.getCurrentCart(request);
		int cartItemsCount = currentCart.getCartItems().size();
		if(cartItemsCount == 0){
			return new ModelAndView(new RedirectView(urlService.generateUrl(FoUrls.HOME, requestUtil.getRequestData(request))));
		}
		
		final CartViewBean cartViewBean = frontofficeViewBeanFactory.buildCartViewBean(requestUtil.getRequestData(request), currentCart);
		modelAndView.addObject("cart", cartViewBean);
		
		modelAndView.addObject("cartForm", formFactory.buildCartForm(request));
		
        return modelAndView;
	}

	@RequestMapping(value = FoUrls.CART_DELIVERY_URL, method = RequestMethod.POST)
	public ModelAndView submitOrderDelivery(final HttpServletRequest request, final HttpServletResponse response, @Valid CartForm cartForm,
								BindingResult result, ModelMap modelMap) throws Exception {
		
		if (result.hasErrors()) {
			return displayOrderDelivery(request, response);
		}
		
		requestUtil.updateCurrentCart(request, Long.parseLong(cartForm.getBillingAddressId()), Long.parseLong(cartForm.getShippingAddressId()));
		
		final String urlRedirect = urlService.generateUrl(FoUrls.CART_ORDER_PAYMENT, requestUtil.getRequestData(request));
        return new ModelAndView(new RedirectView(urlRedirect));
	}

    @ModelAttribute("addresses")
    public List<CustomerAddressViewBean> getAddresses(HttpServletRequest request) {
		List<CustomerAddressViewBean> addressesValues = new ArrayList<CustomerAddressViewBean>();
		try {
			final Customer customer = requestUtil.getCurrentCustomer(request);
			Set<CustomerAddress> addresses = customer.getAddresses();
			for (Iterator<CustomerAddress> iterator = addresses.iterator(); iterator.hasNext();) {
				final CustomerAddress customerAddress = (CustomerAddress) iterator.next();
				addressesValues.add(frontofficeViewBeanFactory.buildCustomeAddressViewBean(requestUtil.getRequestData(request), customerAddress));
			}
			
			Collections.sort(addressesValues, new Comparator<CustomerAddressViewBean>() {
				@Override
				public int compare(CustomerAddressViewBean o1, CustomerAddressViewBean o2) {
					return o1.getAddressName().compareTo(o2.getAddressName());
				}
			});
		} catch (Exception e) {
			logger.error("", e);
		}
		return addressesValues;
    }
}