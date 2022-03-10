import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPersonel } from 'app/shared/model/personel.model';
import { getEntities as getPersonels } from 'app/entities/personel/personel.reducer';
import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { getEntities as getServisTemels } from 'app/entities/servis-temel/servis-temel.reducer';
import { getEntity, updateEntity, createEntity, reset } from './servis-is.reducer';
import { IServisIs } from 'app/shared/model/servis-is.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ServisIsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const personels = useAppSelector(state => state.personel.entities);
  const servisTemels = useAppSelector(state => state.servisTemel.entities);
  const servisIsEntity = useAppSelector(state => state.servisIs.entity);
  const loading = useAppSelector(state => state.servisIs.loading);
  const updating = useAppSelector(state => state.servisIs.updating);
  const updateSuccess = useAppSelector(state => state.servisIs.updateSuccess);
  const handleClose = () => {
    props.history.push('/servis-is' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPersonels({}));
    dispatch(getServisTemels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...servisIsEntity,
      ...values,
      sorumlu: personels.find(it => it.id.toString() === values.sorumlu.toString()),
      yapan: personels.find(it => it.id.toString() === values.yapan.toString()),
      servis: servisTemels.find(it => it.id.toString() === values.servis.toString()),
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
          ...servisIsEntity,
          sorumlu: servisIsEntity?.sorumlu?.id,
          yapan: servisIsEntity?.yapan?.id,
          servis: servisIsEntity?.servis?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servisTakipApp.servisIs.home.createOrEditLabel" data-cy="ServisIsCreateUpdateHeading">
            <Translate contentKey="servisTakipApp.servisIs.home.createOrEditLabel">Create or edit a ServisIs</Translate>
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
                  id="servis-is-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servisTakipApp.servisIs.baslamaTarihi')}
                id="servis-is-baslamaTarihi"
                name="baslamaTarihi"
                data-cy="baslamaTarihi"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.servisIs.tamamlamaTarihi')}
                id="servis-is-tamamlamaTarihi"
                name="tamamlamaTarihi"
                data-cy="tamamlamaTarihi"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                id="servis-is-sorumlu"
                name="sorumlu"
                data-cy="sorumlu"
                label={translate('servisTakipApp.servisIs.sorumlu')}
                type="select"
              >
                <option value="" key="0" />
                {personels
                  ? personels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="servis-is-yapan"
                name="yapan"
                data-cy="yapan"
                label={translate('servisTakipApp.servisIs.yapan')}
                type="select"
              >
                <option value="" key="0" />
                {personels
                  ? personels.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="servis-is-servis"
                name="servis"
                data-cy="servis"
                label={translate('servisTakipApp.servisIs.servis')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/servis-is" replace color="info">
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

export default ServisIsUpdate;
