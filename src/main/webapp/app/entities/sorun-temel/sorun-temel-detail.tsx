import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './sorun-temel.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SorunTemelDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sorunTemelEntity = useAppSelector(state => state.sorunTemel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sorunTemelDetailsHeading">
          <Translate contentKey="servisTakipApp.sorunTemel.detail.title">SorunTemel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sorunTemelEntity.id}</dd>
          <dt>
            <span id="sorunAciklamasi">
              <Translate contentKey="servisTakipApp.sorunTemel.sorunAciklamasi">Sorun Aciklamasi</Translate>
            </span>
          </dt>
          <dd>{sorunTemelEntity.sorunAciklamasi}</dd>
          <dt>
            <span id="sorunTarihi">
              <Translate contentKey="servisTakipApp.sorunTemel.sorunTarihi">Sorun Tarihi</Translate>
            </span>
          </dt>
          <dd>
            {sorunTemelEntity.sorunTarihi ? (
              <TextFormat value={sorunTemelEntity.sorunTarihi} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="cozumAciklamasi">
              <Translate contentKey="servisTakipApp.sorunTemel.cozumAciklamasi">Cozum Aciklamasi</Translate>
            </span>
          </dt>
          <dd>{sorunTemelEntity.cozumAciklamasi}</dd>
          <dt>
            <span id="cozumTarihi">
              <Translate contentKey="servisTakipApp.sorunTemel.cozumTarihi">Cozum Tarihi</Translate>
            </span>
          </dt>
          <dd>
            {sorunTemelEntity.cozumTarihi ? (
              <TextFormat value={sorunTemelEntity.cozumTarihi} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="servisTakipApp.sorunTemel.servis">Servis</Translate>
          </dt>
          <dd>{sorunTemelEntity.servis ? sorunTemelEntity.servis.id : ''}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.sorunTemel.bulan">Bulan</Translate>
          </dt>
          <dd>{sorunTemelEntity.bulan ? sorunTemelEntity.bulan.id : ''}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.sorunTemel.cozen">Cozen</Translate>
          </dt>
          <dd>{sorunTemelEntity.cozen ? sorunTemelEntity.cozen.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/sorun-temel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sorun-temel/${sorunTemelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SorunTemelDetail;
