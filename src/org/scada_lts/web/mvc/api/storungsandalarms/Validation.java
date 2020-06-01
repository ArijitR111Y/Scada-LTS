package org.scada_lts.web.mvc.api.storungsandalarms;
/*
 * (c) 2020 hyski.mateusz@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Create by at Mateusz Hyski
 *
 * @author hyski.mateusz@gmail.com
 */
class Validation {

    protected static final Log LOG = LogFactory.getLog(Validation.class);
    private String validateDoParamIsIntegerAndBetween0And9999(String paramName,String param) {
        if( !param.matches("[^a-z]|[^A-Z]|[0-9]{1,4}")){
            return "Value "+paramName+" is not correct.It should be a number beetwen 0 and 9999.";
        }
        return null;
    }

    /**
     * by meaning ..have correct date format please understand a format date yyyy-mm-dd
     * if given , in this case parameter do not match with dateFormat a user , who invoked rest service
     * have an information about not correct format otherwise any error information is not produced here
     *
     * @param parameter
     * @return String or null
     */
    String doGivenParameterHaveCorrectDateFormat(String parameter){
        if( !parameter.matches(RegexSyntax.DATE_FORMAT_YYYYDASHMMDASHDD)){
            StringBuilder messagePart= new StringBuilder(parameter+" should contain value in format yyyy-mm-dd");
            LOG.info(parameter+" do not contain correct value."+messagePart.toString());
            return messagePart.toString();
        }
        return null;
    }
    static String doGivenParameterIsNumericFrom0To99999(String parameter){
        if( !parameter.matches(RegexSyntax.NUMERIC_VALUE_FROM_0_TO_99999)){
            StringBuilder messagePart= new StringBuilder(parameter+" should contain value from 0 to 9999999");
            LOG.info(parameter+" do not contain correct value."+messagePart.toString());
            return messagePart.toString();
        }
        return null;
    }
    protected String doGivenParameterHaveValueFromScopeSince1To23(String parameter){
        String anyAlphanumericChar="[a-z^][^A-Z^]";
        if( parameter.matches(anyAlphanumericChar+"[1-9]{1}") || parameter.matches(anyAlphanumericChar+"[2]{1}[0-3]{1}")){
            StringBuilder messagePart= new StringBuilder(parameter+" should contain value from 1 to 23");
            LOG.info(parameter+" do not contain correct value."+messagePart);
            return messagePart.toString();
        }
        return null;
    }
    protected String doGivenParameterHaveValue0Or1(String paramName, String param){
        if( !param.equals("0") || param.equals("1")){
            LOG.info(paramName+" do not contain correct value."+paramName+" should contain value 0 or 1");
            return paramName+" should contain value 0 or 1";
        }
        return null;
    }
    protected ResponseEntity<String> backResponseWithValidationError(String parameterName,String validationMessage){
        return new ResponseEntity<String>("Value "+parameterName+" is not valid."+validationMessage, HttpStatus.OK);
    }
    protected ResponseEntity<String> backResponse(String parameterName){
        return new ResponseEntity<String>("Value "+parameterName+" is not valid.", HttpStatus.OK);
    }

}
