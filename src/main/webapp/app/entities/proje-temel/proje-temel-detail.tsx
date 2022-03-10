import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './proje-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProjeTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const projeTemelEntity = useAppSelector(state => state.projeTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="projeTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.projeTemel.detail.title">ProjeTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{projeTemelEntity.id}</dd>
          <dt>
            <span id="projedi">
              <Translate contentKey="servisTakipApp.projeTemel.projedi">Projedi</Translate>
            </span>
          </dt>
          <dd>{projeTemelEntity.projedi}</dd>
          <dt>
            <span id="projeKodu">
              <Translate contentKey="servisTakipApp.projeTemel.projeKodu">Proje Kodu</Translate>
            </span>
          </dt>
          <dd>{projeTemelEntity.projeKodu}</dd>
          <dt>
            <span id="aciklama">
              <Translate contentKey="servisTakipApp.projeTemel.aciklama">Aciklama</Translate>
            </span>
          </dt>
          <dd>{projeTemelEntity.aciklama}</dd>
        </dl>
        <Button tag={Link} to="/proje-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/proje-temel/${projeTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProjeTemelDetail;
