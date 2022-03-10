import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { getEntities as getServisTemels } from 'app/entities/servis-temel/servis-temel.reducer';
import { IProjeTemel } from 'app/shared/model/proje-temel.model';
import { getEntities as getProjeTemels } from 'app/entities/proje-temel/proje-temel.reducer';
import { getEntity, updateEntity, createEntity, reset } from './servis-proje-temel.reducer';
import { IServisProjeTemel } from 'app/shared/model/servis-proje-temel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ServisProjeTemelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const servisTemels = useAppSelector(state => state.servisTemel.entities);
  const projeTemels = useAppSelector(state => state.projeTemel.entities);
  const servisProjeTemelEntity = useAppSelector(state => state.servisProjeTemel.entity);
  const loading = useAppSelector(state => state.servisProjeTemel.loading);
  const updating = useAppSelector(state => state.servisProjeTemel.updating);
  const updateSuccess = useAppSelector(state => state.servisProjeTemel.updateSuccess);
  const handleClose = () => {
    props.history.push('/servis-proje-temel' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getServisTemels({}));
    dispatch(getProjeTemels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...servisProjeTemelEntity,
      ...values,
      servis: servisTemels.find(it => it.id.toString() === values.servis.toString()),
      proje: projeTemels.find(it => it.id.toString() === values.proje.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...servisProjeTemelEntity,
          servis: servisProjeTemelEntity?.servis?.id,
          proje: servisProjeTemelEntity?.proje?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servisTakipApp.servisProjeTemel.home.createOrEditLabel" data-cy="ServisProjeTemelCreateUpdateHeading">
            <Translate contentKey="servisTakipApp.servisProjeTemel.home.createOrEditLabel">Create or edit a ServisProjeTemel</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="servis-proje-temel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servisTakipApp.servisProjeTemel.kullanildigiYer')}
                id="servis-proje-temel-kullanildigiYer"
                name="kullanildigiYer"
                data-cy="kullanildigiYer"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 150, message: translate('entity.validation.maxlength', { max: 150 }) },
                }}
              />
              <ValidatedField
                id="servis-proje-temel-servis"
                name="servis"
                data-cy="servis"
                label={translate('servisTakipApp.servisProjeTemel.servis')}
                type="select"
              >
                <option value="" key="0" />
                {servisTemels
                  ? servisTemels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="servis-proje-temel-proje"
                name="proje"
                data-cy="proje"
                label={translate('servisTakipApp.servisProjeTemel.proje')}
                type="select"
              >
                <option value="" key="0" />
                {projeTemels
                  ? projeTemels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/servis-proje-temel" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ServisProjeTemelUpdate;
