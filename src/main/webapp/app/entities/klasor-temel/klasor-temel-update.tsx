import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { getEntities as getServisTemels } from 'app/entities/servis-temel/servis-temel.reducer';
import { getEntity, updateEntity, createEntity, reset } from './klasor-temel.reducer';
import { IKlasorTemel } from 'app/shared/model/klasor-temel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const KlasorTemelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const servisTemels = useAppSelector(state => state.servisTemel.entities);
  const klasorTemelEntity = useAppSelector(state => state.klasorTemel.entity);
  const loading = useAppSelector(state => state.klasorTemel.loading);
  const updating = useAppSelector(state => state.klasorTemel.updating);
  const updateSuccess = useAppSelector(state => state.klasorTemel.updateSuccess);
  const handleClose = () => {
    props.history.push('/klasor-temel' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getServisTemels({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...klasorTemelEntity,
      ...values,
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
          ...klasorTemelEntity,
          servis: klasorTemelEntity?.servis?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servisTakipApp.klasorTemel.home.createOrEditLabel" data-cy="KlasorTemelCreateUpdateHeading">
            <Translate contentKey="servisTakipApp.klasorTemel.home.createOrEditLabel">Create or edit a KlasorTemel</Translate>
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
                  id="klasor-temel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servisTakipApp.klasorTemel.odasi')}
                id="klasor-temel-odasi"
                name="odasi"
                data-cy="odasi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.klasorTemel.dolabi')}
                id="klasor-temel-dolabi"
                name="dolabi"
                data-cy="dolabi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.klasorTemel.klasoru')}
                id="klasor-temel-klasoru"
                name="klasoru"
                data-cy="klasoru"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.klasorTemel.fihristSirasi')}
                id="klasor-temel-fihristSirasi"
                name="fihristSirasi"
                data-cy="fihristSirasi"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 50, message: translate('entity.validation.maxlength', { max: 50 }) },
                }}
              />
              <ValidatedField
                id="klasor-temel-servis"
                name="servis"
                data-cy="servis"
                label={translate('servisTakipApp.klasorTemel.servis')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/klasor-temel" replace color="info">
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

export default KlasorTemelUpdate;
