package org.lwjglb.engine.graph.particles;

import java.util.List;
import org.lwjglb.engine.items.OpenGLItem;

public interface IParticleEmitter {

    void cleanup();
    
    Particle getBaseParticle();
    
    List<OpenGLItem> getParticles();
}
