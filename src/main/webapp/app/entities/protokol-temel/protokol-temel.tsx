import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './protokol-temel.reducer';
import { IProtokolTemel } from 'app/shared/model/protokol-temel.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ProtokolTemel = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );

  const protokolTemelList = useAppSelector(state => state.protokolTemel.entities);
  const loading = useAppSelector(state => state.protokolTemel.loading);
  const totalItems = useAppSelector(state => state.protokolTemel.totalItems);

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
      <h2 id="protokol-temel-heading" data-cy="ProtokolTemelHeading">
        <Translate contentKey="servisTakipApp.protokolTemel.home.title">Protokol Temels</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="servisTakipApp.protokolTemel.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="servisTakipApp.protokolTemel.home.createLabel">Create new Protokol Temel</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {protokolTemelList && protokolTemelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="servisTakipApp.protokolTemel.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sureDurum')}>
                  <Translate contentKey="servisTakipApp.protokolTemel.sureDurum">Sure Durum</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('sureBitisTarihi')}>
                  <Translate contentKey="servisTakipApp.protokolTemel.sureBitisTarihi">Sure Bitis Tarihi</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('protokolNu')}>
                  <Translate contentKey="servisTakipApp.protokolTemel.protokolNu">Protokol Nu</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('olurYaziNu')}>
                  <Translate contentKey="servisTakipApp.protokolTemel.olurYaziNu">Olur Yazi Nu</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('protokolImzaTarihi')}>
                  <Translate contentKey="servisTakipApp.protokolTemel.protokolImzaTarihi">Protokol Imza Tarihi</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('protokolAciklamasi')}>
                  <Translate contentKey="servisTakipApp.protokolTemel.protokolAciklamasi">Protokol Aciklamasi</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {protokolTemelList.map((protokolTemel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${protokolTemel.id}`} color="link" size="sm">
                      {protokolTemel.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`servisTakipApp.SureDurum.${protokolTemel.sureDurum}`} />
                  </td>
                  <td>
                    {protokolTemel.sureBitisTarihi ? (
                      <TextFormat type="date" value={protokolTemel.sureBitisTarihi} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{protokolTemel.protokolNu}</td>
                  <td>{protokolTemel.olurYaziNu}</td>
                  <td>
                    {protokolTemel.protokolImzaTarihi ? (
                      <TextFormat type="date" value={protokolTemel.protokolImzaTarihi} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{protokolTemel.protokolAciklamasi}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${protokolTemel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${protokolTemel.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
                        to={`${match.url}/${protokolTemel.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              <Translate contentKey="servisTakipApp.protokolTemel.home.notFound">No Protokol Temels found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={protokolTemelList && protokolTemelList.length > 0 ? '' : 'd-none'}>
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

export default ProtokolTemel;
