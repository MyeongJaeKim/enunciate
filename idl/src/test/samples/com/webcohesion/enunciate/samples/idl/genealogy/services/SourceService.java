/*
 * Copyright 2006-2008 Web Cohesion
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webcohesion.enunciate.samples.idl.genealogy.services;

import com.webcohesion.enunciate.samples.idl.genealogy.cite.Source;
import com.webcohesion.enunciate.samples.idl.genealogy.data.Event;
import com.webcohesion.enunciate.samples.idl.genealogy.cite.InfoSet;

import javax.jws.Oneway;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.WebResult;
import javax.jws.soap.SOAPBinding;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 * The source service is used to access and store source information about genealogical information.
 * Compared to the person service, this one is much more customized.
 *
 * @author Ryan Heaton
 */
@WebService (
  name = "source-service",
  targetNamespace = "http://enunciate.webcohesion.com/samples/full",
  serviceName = "source-service",
  portName = "source-service-port"
)
public interface SourceService {

  /**
   * Adds a source to the database.  Example of a one-way method.
   *
   * @param source The source to add to the database.
   */
  @Oneway
  @POST
  void addSource(Source source);

  /**
   * Reads a source from the database.
   *
   * @param id The id of the source to read from the database.
   * @return The source.
   * @throws ServiceException If a source couldn't be read from the database.
   * @throws UnknownSourceException If no source by that id was found in the database.
   */
  @GET
  @Path ("{id}")
  Source getSource(@PathParam("id") String id) throws ServiceException, UnknownSourceException;

  /**
   * Adds an infoset to a specified source.
   *
   * @param sourceId The id of the source to which to add the infoset.
   * @param infoSet The infoset to add to the source.
   * @return The id of the infoset that was added.
   * @throws ServiceException If the infoset couldn't be added to the source.
   */
  @SOAPBinding (
    style = SOAPBinding.Style.RPC
  )
  String addInfoSet(String sourceId, InfoSet infoSet) throws ServiceException;

  /**
   * Adds a bunch of events to an infoset.
   *
   * @param infoSetId The id of the infoset to which to add the events.
   * @param assertions The events that are to be added to the infoset.
   * @param contributorId The if of the contributor adding the events to the infoset.
   * @return Some message.
   * @throws ServiceException If the events couldn't be added to the infoset.
   */
  @WebResult (
    header = true,
    name = "resultOfAddingEvents"
  )
  String addEvents(String infoSetId, Event[] assertions, @WebParam (header = true, name="contributorId") String contributorId) throws ServiceException;
}
