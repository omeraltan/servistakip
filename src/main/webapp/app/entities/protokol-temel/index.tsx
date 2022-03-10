import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ProtokolTemel from './protokol-temel';
import ProtokolTemelDetail from './protokol-temel-detail';
import ProtokolTemelUpdate from './protokol-temel-update';
import ProtokolTemelDeleteDialog from './protokol-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ProtokolTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ProtokolTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ProtokolTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={ProtokolTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ProtokolTemelDeleteDialog} />
  </>
);

export default Routes;
