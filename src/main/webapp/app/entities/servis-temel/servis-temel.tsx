import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './servis-temel.reducer';
import { IServisTemel } from 'app/shared/model/servis-temel.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ServisTemel = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const servisTemelList = useAppSelector(state => state.servisTemel.entities);
  const loading = useAppSelector(state => state.servisTemel.loading);
  const totalItems = useAppSelector(state => state.servisTemel.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (props.location.search !== endURL) {
      props.history.push(`${props.location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(props.location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [props.location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="servis-temel-heading" data-cy="ServisTemelHeading">
        <Translate contentKey="servisTakipApp.servisTemel.home.title">Servis Temels</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="servisTakipApp.servisTemel.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="servisTakipApp.servisTemel.home.createLabel">Create new Servis Temel</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {servisTemelList && servisTemelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="servisTakipApp.servisTemel.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('servisAdi')}>
                  <Translate contentKey="servisTakipApp.servisTemel.servisAdi">Servis Adi</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('servisUrl')}>
                  <Translate contentKey="servisTakipApp.servisTemel.servisUrl">Servis Url</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('servisVeriTip')}>
                  <Translate contentKey="servisTakipApp.servisTemel.servisVeriTip">Servis Veri Tip</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('aciklamasi')}>
                  <Translate contentKey="servisTakipApp.servisTemel.aciklamasi">Aciklamasi</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('dosyasi')}>
                  <Translate contentKey="servisTakipApp.servisTemel.dosyasi">Dosyasi</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('resmi')}>
                  <Translate contentKey="servisTakipApp.servisTemel.resmi">Resmi</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('servisSekli')}>
                  <Translate contentKey="servisTakipApp.servisTemel.servisSekli">Servis Sekli</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('servisTipi')}>
                  <Translate contentKey="servisTakipApp.servisTemel.servisTipi">Servis Tipi</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('baglantiSekli')}>
                  <Translate contentKey="servisTakipApp.servisTemel.baglantiSekli">Baglanti Sekli</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('servisDurum')}>
                  <Translate contentKey="servisTakipApp.servisTemel.servisDurum">Servis Durum</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('anlikSorgu')}>
                  <Translate contentKey="servisTakipApp.servisTemel.anlikSorgu">Anlik Sorgu</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('yiginVeri')}>
                  <Translate contentKey="servisTakipApp.servisTemel.yiginVeri">Yigin Veri</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('gds')}>
                  <Translate contentKey="servisTakipApp.servisTemel.gds">Gds</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="servisTakipApp.servisTemel.protokol">Protokol</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="servisTakipApp.servisTemel.bakanlik">Bakanlik</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {servisTemelList.map((servisTemel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${servisTemel.id}`} color="link" size="sm">
                      {servisTemel.id}
                    </Button>
                  </td>
                  <td>{servisTemel.servisAdi}</td>
                  <td>{servisTemel.servisUrl}</td>
                  <td>{servisTemel.servisVeriTip}</td>
                  <td>{servisTemel.aciklamasi}</td>
                  <td>
                    {servisTemel.dosyasi ? (
                      <div>
                        {servisTemel.dosyasiContentType ? (
                          <a onClick={openFile(servisTemel.dosyasiContentType, servisTemel.dosyasi)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {servisTemel.dosyasiContentType}, {byteSize(servisTemel.dosyasi)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    {servisTemel.resmi ? (
                      <div>
                        {servisTemel.resmiContentType ? (
                          <a onClick={openFile(servisTemel.resmiContentType, servisTemel.resmi)}>
                            <img src={`data:${servisTemel.resmiContentType};base64,${servisTemel.resmi}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {servisTemel.resmiContentType}, {byteSize(servisTemel.resmi)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>
                    <Translate contentKey={`servisTakipApp.ServisSekli.${servisTemel.servisSekli}`} />
                  </td>
                  <td>
                    <Translate contentKey={`servisTakipApp.ServisTipi.${servisTemel.servisTipi}`} />
                  </td>
                  <td>
                    <Translate contentKey={`servisTakipApp.BaglantiSekli.${servisTemel.baglantiSekli}`} />
                  </td>
                  <td>
                    <Translate contentKey={`servisTakipApp.ServisDurum.${servisTemel.servisDurum}`} />
                  </td>
                  <td>{servisTemel.anlikSorgu}</td>
                  <td>{servisTemel.yiginVeri}</td>
                  <td>{servisTemel.gds}</td>
                  <td>
                    {servisTemel.protokol ? <Link to={`protokol-temel/${servisTemel.protokol.id}`}>{servisTemel.protokol.id}</Link> : ''}
                  </td>
                  <td>
                    {servisTemel.bakanlik ? <Link to={`kurum-temel/${servisTemel.bakanlik.id}`}>{servisTemel.bakanlik.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${servisTemel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${servisTemel.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${servisTemel.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="servisTakipApp.servisTemel.home.notFound">No Servis Temels found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={servisTemelList && servisTemelList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default ServisTemel;
