package com.webcohesion.enunciate.examples.epcis.impl;

import epcglobal.epcis.wsdl._1.*;
import epcglobal.epcis_query.xsd._1.*;

import javax.jws.WebService;

/**
 * @author Ryan Heaton
 */
@WebService (
  endpointInterface = "epcglobal.epcis.wsdl._1.EPCISServicePortType"
)
public class EPCISServiceImpl implements EPCISServicePortType {

  public ArrayOfString getQueryNames(EmptyParms parms) throws ImplementationExceptionResponse, SecurityExceptionResponse, ValidationExceptionResponse {
    ArrayOfString queryNames = new ArrayOfString();
    queryNames.getString().add("hello");
    queryNames.getString().add("how");
    queryNames.getString().add("are");
    queryNames.getString().add("you?");
    return queryNames;
  }

  public VoidHolder subscribe( Subscribe parms) throws DuplicateSubscriptionExceptionResponse, ImplementationExceptionResponse, InvalidURIExceptionResponse, NoSuchNameExceptionResponse, QueryParameterExceptionResponse, QueryTooComplexExceptionResponse, SecurityExceptionResponse, SubscribeNotPermittedExceptionResponse, SubscriptionControlsExceptionResponse, ValidationExceptionResponse {
    return null;
  }

  public VoidHolder unsubscribe( Unsubscribe parms) throws ImplementationExceptionResponse, NoSuchSubscriptionExceptionResponse, SecurityExceptionResponse, ValidationExceptionResponse {
    return null;
  }

  public ArrayOfString getSubscriptionIDs( GetSubscriptionIDs parms) throws ImplementationExceptionResponse, NoSuchNameExceptionResponse, SecurityExceptionResponse, ValidationExceptionResponse {
    return null;
  }

  public QueryResults poll(Poll parms) throws ImplementationExceptionResponse, NoSuchNameExceptionResponse, QueryParameterExceptionResponse, QueryTooComplexExceptionResponse, QueryTooLargeExceptionResponse, SecurityExceptionResponse, ValidationExceptionResponse {
    return null;
  }

  public String getStandardVersion( EmptyParms parms ) throws ImplementationExceptionResponse, SecurityExceptionResponse, ValidationExceptionResponse {
    return null;
  }

  public String getVendorVersion( EmptyParms parms ) throws ImplementationExceptionResponse, SecurityExceptionResponse, ValidationExceptionResponse {
    return null;
  }
}
