package com.webcohesion.enunciate.modules.jaxb.api.impl;

import com.webcohesion.enunciate.api.datatype.*;
import com.webcohesion.enunciate.facets.FacetFilter;
import com.webcohesion.enunciate.modules.jaxb.model.Attribute;
import com.webcohesion.enunciate.modules.jaxb.model.ComplexTypeDefinition;
import com.webcohesion.enunciate.modules.jaxb.model.Element;
import com.webcohesion.enunciate.modules.jaxb.model.types.XmlClassType;
import com.webcohesion.enunciate.modules.jaxb.model.types.XmlType;

import java.util.*;

/**
 * @author Ryan Heaton
 */
public class ComplexDataTypeImpl extends DataTypeImpl {

  private final ComplexTypeDefinition typeDefinition;

  public ComplexDataTypeImpl(ComplexTypeDefinition typeDefinition) {
    super(typeDefinition);
    this.typeDefinition = typeDefinition;
  }

  @Override
  public BaseType getBaseType() {
    return BaseType.object;
  }

  @Override
  public List<? extends Value> getValues() {
    return null;
  }

  @Override
  public List<? extends Property> getProperties() {
    ArrayList<Property> properties = new ArrayList<Property>();
    FacetFilter facetFilter = this.typeDefinition.getContext().getContext().getConfiguration().getFacetFilter();

    List<Property> attributeProperties = new ArrayList<Property>();
    for (Attribute attribute : this.typeDefinition.getAttributes()) {
      if (!facetFilter.accept(attribute)) {
        continue;
      }

      attributeProperties.add(new PropertyImpl(attribute));
    }

    //sort the attributes by name, then add them to the list.
    Collections.sort(attributeProperties, new Comparator<Property>() {
      @Override
      public int compare(Property o1, Property o2) {
        return o1.getName().compareTo(o2.getName());
      }
    });
    properties.addAll(attributeProperties);

    if (this.typeDefinition.getValue() != null) {
      properties.add(new PropertyImpl(this.typeDefinition.getValue()));
    }
    else {
      List<Property> elementProperties = new ArrayList<Property>();
      for (Element element : this.typeDefinition.getElements()) {
        if (!facetFilter.accept(element)) {
          continue;
        }

        boolean wrapped = element.isWrapped();
        String wrapperName = wrapped ? element.getWrapperName() : null;
        String wrapperNamespace = wrapped ? element.getWrapperNamespace() : null;
        for (Element choice : element.getChoices()) {
          elementProperties.add(wrapped ? new WrappedPropertyImpl(choice, wrapperName, wrapperNamespace) : new PropertyImpl(choice));
        }
      }

      Collections.sort(elementProperties, new Comparator<Property>() {
        @Override
        public int compare(Property o1, Property o2) {
          return o1.getName().compareTo(o2.getName());
        }
      });
      properties.addAll(elementProperties);
    }

    return properties;
  }

  public List<? extends Property> getRequiredProperties() {
    ArrayList<Property> requiredProperties = new ArrayList<Property>();
    for (Property property : getProperties()) {
      if (property.isRequired()) {
        requiredProperties.add(property);
      }
    }
    return requiredProperties;
  }

  @Override
  public List<DataTypeReference> getSupertypes() {
    ArrayList<DataTypeReference> supertypes = null;

    XmlType supertype = this.typeDefinition.getBaseType();
    while (supertype != null) {
      if (supertypes == null) {
        supertypes = new ArrayList<DataTypeReference>();
      }

      supertypes.add(new DataTypeReferenceImpl(supertype, false));
      supertype = supertype instanceof XmlClassType ?
        ((XmlClassType)supertype).getTypeDefinition() instanceof ComplexTypeDefinition ?
          ((XmlClassType)supertype).getTypeDefinition().getBaseType()
          : null
        : null;
    }

    return supertypes;
  }

  @Override
  public Example getExample() {
    return new ExampleImpl(this.typeDefinition);
  }

  @Override
  public Map<String, String> getPropertyMetadata() {
    Map<String, String> propertyMetadata = new LinkedHashMap<String, String>();
    propertyMetadata.put("type", "type");
    propertyMetadata.put("namespaceInfo", "namespace");
    propertyMetadata.put("minMaxOccurs", "min/max occurs");

    //if any elements have a default value, show that, too.
    boolean showDefaultValue = false;
    boolean showWrapper = false;
    for (Element element : this.typeDefinition.getElements()) {
      for (Element choice : element.getChoices()) {
        if (choice.getDefaultValue() != null) {
          showDefaultValue = true;
        }
      }

      if (element.isWrapped()) {
        showWrapper = true;
      }
    }

    if (showDefaultValue) {
      propertyMetadata.put("defaultValue", "default");
    }

    if (showWrapper) {
      propertyMetadata.put("wrapper", "wrapped by");
    }

    return propertyMetadata;
  }
}
