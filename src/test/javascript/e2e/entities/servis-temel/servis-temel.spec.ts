import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ServisTemelComponentsPage from './servis-temel.page-object';
import ServisTemelUpdatePage from './servis-temel-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('ServisTemel e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let servisTemelComponentsPage: ServisTemelComponentsPage;
  let servisTemelUpdatePage: ServisTemelUpdatePage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    servisTemelComponentsPage = new ServisTemelComponentsPage();
    servisTemelComponentsPage = await servisTemelComponentsPage.goToPage(navBarPage);
  });

  it('should load ServisTemels', async () => {
    expect(await servisTemelComponentsPage.title.getText()).to.match(/Servis Temels/);
    expect(await servisTemelComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete ServisTemels', async () => {
    const beforeRecordsCount = (await isVisible(servisTemelComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(servisTemelComponentsPage.table);
    servisTemelUpdatePage = await servisTemelComponentsPage.goToCreateServisTemel();
    await servisTemelUpdatePage.enterData();
    expect(await isVisible(servisTemelUpdatePage.saveButton)).to.be.false;

    expect(await servisTemelComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(servisTemelComponentsPage.table);
    await waitUntilCount(servisTemelComponentsPage.records, beforeRecordsCount + 1);
    expect(await servisTemelComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await servisTemelComponentsPage.deleteServisTemel();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(servisTemelComponentsPage.records, beforeRecordsCount);
      expect(await servisTemelComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(servisTemelComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
