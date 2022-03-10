import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './servis-proje-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ServisProjeTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const servisProjeTemelEntity = useAppSelector(state => state.servisProjeTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="servisProjeTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.servisProjeTemel.detail.title">ServisProjeTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{servisProjeTemelEntity.id}</dd>
          <dt>
            <span id="kullanildigiYer">
              <Translate contentKey="servisTakipApp.servisProjeTemel.kullanildigiYer">Kullanildigi Yer</Translate>
            </span>
          </dt>
          <dd>{servisProjeTemelEntity.kullanildigiYer}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.servisProjeTemel.servis">Servis</Translate>
          </dt>
          <dd>{servisProjeTemelEntity.servis ? servisProjeTemelEntity.servis.id : ''}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.servisProjeTemel.proje">Proje</Translate>
          </dt>
          <dd>{servisProjeTemelEntity.proje ? servisProjeTemelEntity.proje.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/servis-proje-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servis-proje-temel/${servisProjeTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServisProjeTemelDetail;
