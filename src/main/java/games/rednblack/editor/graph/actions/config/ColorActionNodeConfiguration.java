package games.rednblack.editor.graph.actions.config;

import games.rednblack.editor.graph.GraphNodeInputImpl;
import games.rednblack.editor.graph.GraphNodeOutputImpl;
import games.rednblack.editor.graph.actions.ActionFieldType;
import games.rednblack.editor.graph.config.NodeConfigurationImpl;

import static games.rednblack.editor.graph.actions.ActionFieldType.*;

public class ColorActionNodeConfiguration extends NodeConfigurationImpl<ActionFieldType> {

    public ColorActionNodeConfiguration() {
        super("Action", "Color", "Action");

        addNodeInput(
                new GraphNodeInputImpl<ActionFieldType>("color", "Color", true, Color));

        addNodeInput(
                new GraphNodeInputImpl<ActionFieldType>("duration", "Duration", false, ActionFieldType.Float));

        addNodeInput(
                new GraphNodeInputImpl<ActionFieldType>("interpolation", "Interpolation", false, Interpolation));

        addNodeOutput(
                new GraphNodeOutputImpl<>("action", "Action", Action));
    }
}
