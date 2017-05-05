package org.estatio.capex.dom.state;

import java.util.Objects;

import javax.inject.Inject;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;
import org.apache.isis.applib.services.metamodel.MetaModelService3;

import org.estatio.capex.dom.task.Task;

/**
 * Created by Dan on 05/05/2017.
 */
@DomainService(nature = NatureOfService.DOMAIN)
public abstract class StateTransitionServiceSupportAbstract<
        DO,
        ST extends StateTransition<DO, ST, STT, S>,
        STT extends StateTransitionChart<DO, ST, STT, S>,
        S extends State<S>
        > implements StateTransitionServiceSupport<DO, ST, STT, S> {

    private final Class<STT> stateTransitionTypeClass;
    private final Class<ST> stateTransitionClass;
    private final S initialState;

    public StateTransitionServiceSupportAbstract(
            final Class<STT> stateTransitionTypeClass,
            final Class<ST> stateTransitionClass,
            final S initialState) {

        this.stateTransitionTypeClass = stateTransitionTypeClass;
        this.stateTransitionClass = stateTransitionClass;
        this.initialState = initialState;
    }

    @Override
    public boolean supports(final StateTransitionChart<?, ?, ?, ?> transitionType) {
        return stateTransitionTypeClass.isAssignableFrom(transitionType.getClass());
    }

    @Override
    public boolean supports(final String transitionType) {
        String objectType = metaModelServicwe3.toObjectType(stateTransitionClass);
        return Objects.equals(objectType, transitionType);
    }

    @Override
    public ST currentTransitionOf(final DO domainObject) {
        return getRepository().findByDomainObjectAndIncomplete(domainObject);
    }

    @Override
    public S currentStateOf(final DO domainObject) {
        final ST currentTransitionIfAny = currentTransitionOf(domainObject);
        return currentTransitionIfAny != null
                ? currentTransitionIfAny.getFromState()
                : initialState;
    }

    @Override
    public STT[] allTransitionTypes() {
        return stateTransitionTypeClass.getEnumConstants();
    }

    @Override
    public ST findIncomplete(final DO domainObject) {
        return getRepository().findByDomainObjectAndIncomplete(domainObject);
    }

    @Override
    public ST findFor(final Task task) {
        return getRepository().findByTask(task);
    }

    protected abstract StateTransitionRepository<DO, ST, STT, S> getRepository();

    @Inject
    protected MetaModelService3 metaModelServicwe3;
}
