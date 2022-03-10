import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import KurumTemel from './kurum-temel';
import KurumTemelDetail from './kurum-temel-detail';
import KurumTemelUpdate from './kurum-temel-update';
import KurumTemelDeleteDialog from './kurum-temel-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={KurumTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={KurumTemelUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={KurumTemelDetail} />
      <ErrorBoundaryRoute path={match.url} component={KurumTemel} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={KurumTemelDeleteDialog} />
  </>
);

export default Routes;
