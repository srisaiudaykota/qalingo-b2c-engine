/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.web.mvc.factory.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import org.hoteia.qalingo.core.service.UrlService;
import org.hoteia.qalingo.core.web.mvc.form.QuickSearchForm;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.hoteia.qalingo.web.mvc.factory.FormFactory;
import org.hoteia.qalingo.web.mvc.form.EngineSettingValueForm;
import org.hoteia.qalingo.web.mvc.form.PaymentGatewayForm;
import org.hoteia.qalingo.web.mvc.form.UserForm;

/**
 * 
 */
@Service("formFactory")
public class FormFactoryImpl implements FormFactory {

    @Autowired
    protected CoreMessageSource coreMessageSource;

    @Autowired
    protected RequestUtil requestUtil;

    @Autowired
    protected UrlService urlService;

    public void buildEngineSettingValueEditForm(final HttpServletRequest request, final ModelAndView modelAndView, final EngineSettingValue engineSettingValue) throws Exception {
        final EngineSettingValueForm engineSettingValueForm = new EngineSettingValueForm();
        engineSettingValueForm.setId(engineSettingValue.getId().toString());
        engineSettingValueForm.setContext(engineSettingValue.getContext());
        engineSettingValueForm.setValue(engineSettingValue.getValue());
        modelAndView.addObject("engineSettingValueForm", engineSettingValueForm);
    }

    public void buildPaymentGatewayForm(final HttpServletRequest request, final ModelAndView modelAndView, final AbstractPaymentGateway paymentGateway) throws Exception {
        final PaymentGatewayForm paymentGatewayForm = new PaymentGatewayForm();
        paymentGatewayForm.setId(paymentGateway.getId().toString());
        modelAndView.addObject("paymentGatewayForm", paymentGatewayForm);
    }

    public void buildUserForm(final HttpServletRequest request, final ModelAndView modelAndView, final User user) throws Exception {
        final UserForm userForm = new UserForm();
        userForm.setId(user.getId().toString());
        userForm.setLogin(user.getLogin());
        userForm.setFirstname(user.getFirstname());
        userForm.setLastname(user.getLastname());
        userForm.setEmail(user.getEmail());
        userForm.setActive(user.isActive());
        modelAndView.addObject("userForm", userForm);
    }

    public void buildEngineSettingQuickSearchForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
        final QuickSearchForm engineSettingQuickSearchForm = new QuickSearchForm();
        modelAndView.addObject("engineSettingQuickSearchForm", engineSettingQuickSearchForm);
    }

    public void buildUserQuickSearchForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
        final QuickSearchForm userQuickSearchForm = new QuickSearchForm();
        modelAndView.addObject("userQuickSearchForm", userQuickSearchForm);
    }

    public void buildBatchQuickSearchForm(final HttpServletRequest request, final ModelAndView modelAndView) throws Exception {
        final QuickSearchForm batchQuickSearchForm = new QuickSearchForm();
        modelAndView.addObject("batchQuickSearchForm", batchQuickSearchForm);
    }

}
