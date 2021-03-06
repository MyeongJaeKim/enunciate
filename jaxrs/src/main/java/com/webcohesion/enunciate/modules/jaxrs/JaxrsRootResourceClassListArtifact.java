package com.webcohesion.enunciate.modules.jaxrs;

import com.webcohesion.enunciate.Enunciate;
import com.webcohesion.enunciate.artifacts.BaseArtifact;
import com.webcohesion.enunciate.modules.jaxrs.model.RootResource;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ryan Heaton
 */
public class JaxrsRootResourceClassListArtifact extends BaseArtifact {

  private final EnunciateJaxrsContext jaxrsContext;
  private final Date created = new Date();

  public JaxrsRootResourceClassListArtifact(EnunciateJaxrsContext jaxrsContext) {
    super(JaxrsModule.NAME, "jaxrs-resource-classes.list");
    this.jaxrsContext = jaxrsContext;
    setBelongsOnServerSideClasspath(true);
  }

  @Override
  public String getName() {
    return "jaxrs-resource-classes.list";
  }

  @Override
  public String getDescription() {
    return "A plain text file that contains the list of all JAX-RS root resources in the current project.";
  }

  @Override
  public boolean isPublic() {
    return false;
  }

  @Override
  public Date getCreated() {
    return this.created;
  }

  @Override
  public void exportTo(File fileOrDirectory, Enunciate enunciate) throws IOException {
    FileWriter out = new FileWriter(fileOrDirectory.isDirectory() ? new File(fileOrDirectory, getName()) : fileOrDirectory);

    Set<String> written = new HashSet<String>();
    for (RootResource resource : this.jaxrsContext.getRootResources()) {
      if (written.add(resource.getQualifiedName().toString())) {
        out.write(resource.getQualifiedName() + "\n");
      }
    }

    out.flush();
    out.close();
  }

  @Override
  public long getSize() {
    return -1;
  }
}
