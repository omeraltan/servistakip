import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { getEntities as getServisTemels } from 'app/entities/servis-temel/servis-temel.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tablo-temel.reducer';
import { ITabloTemel } from 'app/shared/model/tablo-temel.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TabloTemelUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const servisTemels = useAppSelector(state => state.servisTemel.entities);
  const tabloTemelEntity = useAppSelector(state => state.tabloTemel.entity);
  const loading = useAppSelector(state => state.tabloTemel.loading);
  const updating = useAppSelector(state => state.tabloTemel.updating);
  const updateSuccess = useAppSelector(state => state.tabloTemel.updateSuccess);
  const handleClose = () => {
    props.history.push('/tablo-temel' + props.location.search);
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
      ...tabloTemelEntity,
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
          ...tabloTemelEntity,
          servis: tabloTemelEntity?.servis?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="servisTakipApp.tabloTemel.home.createOrEditLabel" data-cy="TabloTemelCreateUpdateHeading">
            <Translate contentKey="servisTakipApp.tabloTemel.home.createOrEditLabel">Create or edit a TabloTemel</Translate>
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
                  id="tablo-temel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('servisTakipApp.tabloTemel.gercekVeritabani')}
                id="tablo-temel-gercekVeritabani"
                name="gercekVeritabani"
                data-cy="gercekVeritabani"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.tabloTemel.gercekLogTablo')}
                id="tablo-temel-gercekLogTablo"
                name="gercekLogTablo"
                data-cy="gercekLogTablo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.tabloTemel.gercekaciklamasi')}
                id="tablo-temel-gercekaciklamasi"
                name="gercekaciklamasi"
                data-cy="gercekaciklamasi"
                type="text"
                validate={{
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 500, message: translate('entity.validation.maxlength', { max: 500 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.tabloTemel.testVeritabani')}
                id="tablo-temel-testVeritabani"
                name="testVeritabani"
                data-cy="testVeritabani"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.tabloTemel.testLogTablo')}
                id="tablo-temel-testLogTablo"
                name="testLogTablo"
                data-cy="testLogTablo"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 100, message: translate('entity.validation.maxlength', { max: 100 }) },
                }}
              />
              <ValidatedField
                label={translate('servisTakipApp.tabloTemel.testaciklamasi')}
                id="tablo-temel-testaciklamasi"
                name="testaciklamasi"
                data-cy="testaciklamasi"
                type="text"
                validate={{
                  minLength: { value: 0, message: translate('entity.validation.minlength', { min: 0 }) },
                  maxLength: { value: 500, message: translate('entity.validation.maxlength', { max: 500 }) },
                }}
              />
              <ValidatedField
                id="tablo-temel-servis"
                name="servis"
                data-cy="servis"
                label={translate('servisTakipApp.tabloTemel.servis')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tablo-temel" replace color="info">
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

export default TabloTemelUpdate;
