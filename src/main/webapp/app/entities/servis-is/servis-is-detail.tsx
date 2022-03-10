import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './servis-is.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ServisIsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const servisIsEntity = useAppSelector(state => state.servisIs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="servisIsDetailsHeading">
          <Translate contentKey="servisTakipApp.servisIs.detail.title">ServisIs</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{servisIsEntity.id}</dd>
          <dt>
            <span id="baslamaTarihi">
              <Translate contentKey="servisTakipApp.servisIs.baslamaTarihi">Baslama Tarihi</Translate>
            </span>
          </dt>
          <dd>
            {servisIsEntity.baslamaTarihi ? (
              <TextFormat value={servisIsEntity.baslamaTarihi} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="tamamlamaTarihi">
              <Translate contentKey="servisTakipApp.servisIs.tamamlamaTarihi">Tamamlama Tarihi</Translate>
            </span>
          </dt>
          <dd>
            {servisIsEntity.tamamlamaTarihi ? (
              <TextFormat value={servisIsEntity.tamamlamaTarihi} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="servisTakipApp.servisIs.sorumlu">Sorumlu</Translate>
          </dt>
          <dd>{servisIsEntity.sorumlu ? servisIsEntity.sorumlu.id : ''}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.servisIs.yapan">Yapan</Translate>
          </dt>
          <dd>{servisIsEntity.yapan ? servisIsEntity.yapan.id : ''}</dd>
          <dt>
            <Translate contentKey="servisTakipApp.servisIs.servis">Servis</Translate>
          </dt>
          <dd>{servisIsEntity.servis ? servisIsEntity.servis.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/servis-is" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/servis-is/${servisIsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServisIsDetail;
