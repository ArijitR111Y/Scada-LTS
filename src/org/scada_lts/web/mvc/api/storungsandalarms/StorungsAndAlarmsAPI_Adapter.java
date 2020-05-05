package org.scada_lts.web.mvc.api.storungsandalarms;

/*
 * (c) 2018 hyski.mateusz@gmail.com
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

import com.serotonin.mango.Common;
import com.serotonin.mango.vo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.scada_lts.dao.PointValuesStorungsAndAlarms;
import org.scada_lts.dao.storungsAndAlarms.StorungsAndAlarms;
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
@Controller
public class StorungsAndAlarmsAPI_Adapter {

    private static final Log LOG = LogFactory.getLog(StorungsAndAlarmsAPI_Adapter.class);
    private static PointValuesStorungsAndAlarms pointValuesStorungsAndAlarms =new StorungsAndAlarms();
    private boolean validate(String paramName,String param){
        if( !param.equals("0") || param.equals("1")){
            LOG.info(paramName+" is empty."+paramName+" can't be empty");
            return false;
        }
        return true;
    }
    @RequestMapping(value = "/api/alarms/{sortDataActivation}/{sortDataInactivation}/{sortPointName}/{sortStatus}", method = RequestMethod.GET)
    public ResponseEntity<String> getAlarms(
            @PathVariable("sortDataActivation") String sortDataActivation,
            @PathVariable("sortDataInactivation") String sortDataInactivation,
            @PathVariable("sortPointName") String sortPointName,
            @PathVariable("sortStatus") String sortStatus,
            HttpServletRequest request
    )
    {
        LOG.info("/api/alarms/{sortDataActivation}/{sortDataInactivation}/{sortPointName}/{sortStatus}");
        if ( !validate("SortDataActivation",sortDataActivation) ){
            new ResponseEntity<String>("Value SortDataActivation is empty", HttpStatus.OK);
        }
        if ( !validate("SortDataInactivation",sortDataInactivation) ){
            new ResponseEntity<String>("Value SortDataInactivation is empty", HttpStatus.OK);
        }
        if ( !validate("SortPointName",sortPointName) ) {
            new ResponseEntity<String>("Value SortPointName is empty", HttpStatus.OK);
        }
        if ( !validate("SortStatus",sortStatus) ) {
            new ResponseEntity<String>("Value SortStatus is empty", HttpStatus.OK);
        }
        try {
                User user = Common.getUser(request);
                if (user != null && user.isAdmin()) {
                    JSONObject result=null;
                    if(sortStatus.equals("0")) {
                        result = pointValuesStorungsAndAlarms.getStorungs(0);
                    }
                    if(sortStatus.equals("1")){
                        result = pointValuesStorungsAndAlarms.getStorungs(1);
                    }
                    return new ResponseEntity<String>( result.toString() , HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
                }
        } catch (Exception e) {
            return new ResponseEntity<String>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}